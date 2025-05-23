/*
 * This file is generated by jOOQ.
 */
package com.vidsummize.jooq;


import com.vidsummize.jooq.tables.Video;
import com.vidsummize.jooq.tables.records.VideoRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<VideoRecord> VIDEO_PKEY = Internal.createUniqueKey(Video.VIDEO, DSL.name("video_pkey"), new TableField[] { Video.VIDEO.ID }, true);
}
