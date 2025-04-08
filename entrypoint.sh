#!/bin/bash

echo "Starting Ollama server..."
ollama serve &
SERVE_PID=$!

echo "Waiting for Ollama server to be active..."
until curl -s http://localhost:11434 > /dev/null; do
  sleep 1
done

echo "Checking if deepseek-llm model is already pulled..."
if ! ollama list | grep -q 'deepseek-llm'; then
  echo "Model not found. Pulling deepseek-llm model..."
  ollama pull deepseek-llm
else
  echo "Model deepseek-llm already available."
fi

wait $SERVE_PID
