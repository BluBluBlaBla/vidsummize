package com.vidsummize.integrations;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

@Component
public class WhisperIntegrationImpl implements WhisperIntegration {

    private final Dotenv dotenv = Dotenv.load();

    @Override
    public String transcribe(String youtubeUrl) {
        try {

            String ytdlpCommand = dotenv.get("YTDLP_COMMAND");
            if (ytdlpCommand == null || ytdlpCommand.isEmpty()) {
                throw new IllegalStateException("A variável de ambiente YTDLP_COMMAND não foi definida no .env");
            }

            File workingDir = new File("/home/lynon/projects/my-projects/vidsummize/temp");
            if (!workingDir.exists()) {
                workingDir.mkdirs();
            }

            ProcessBuilder ytDlpProcessBuilder = new ProcessBuilder(
                    ytdlpCommand,
                    "-x", "--audio-format", "mp3",
                    "-o", "audio.mp3",
                    youtubeUrl
            );
            ytDlpProcessBuilder.directory(workingDir);
            ytDlpProcessBuilder.redirectErrorStream(true);
            Process ytDlpProcess = ytDlpProcessBuilder.start();

            BufferedReader ytReader = new BufferedReader(new InputStreamReader(ytDlpProcess.getInputStream()));
            String ytLine;
            while ((ytLine = ytReader.readLine()) != null) {
                System.out.println(ytLine);
            }
            ytDlpProcess.waitFor();

            String whisperExecutable = dotenv.get("WHISPER_CPP_PATH");
            if (whisperExecutable == null || whisperExecutable.isEmpty()) {
                throw new IllegalStateException("A variável de ambiente WHISPER_CPP_PATH não foi definida no .env");
            }

            String modelo = "/home/lynon/projects/my-projects/whisper/whisper.cpp/models/ggml-base.en.bin";

            String audioFilePath = new File(workingDir, "audio.mp3").getAbsolutePath();

            ProcessBuilder whisperProcessBuilder = new ProcessBuilder(
                    whisperExecutable,
                    "-m", modelo,
                    "-f", audioFilePath
            );

            whisperProcessBuilder.directory(workingDir);
            whisperProcessBuilder.redirectErrorStream(true);
            Process whisperProcess = whisperProcessBuilder.start();

            BufferedReader whisperReader = new BufferedReader(new InputStreamReader(whisperProcess.getInputStream()));
            StringBuilder transcription = new StringBuilder();
            String whisperLine;
            while ((whisperLine = whisperReader.readLine()) != null) {
                transcription.append(whisperLine).append("\n");
            }
            whisperProcess.waitFor();

            File audioFile = new File(workingDir, "audio.mp3");
            if (audioFile.exists()) {
                 audioFile.delete();
            }

            return transcription.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro na transcrição: " + e.getMessage();
        }
    }
}
