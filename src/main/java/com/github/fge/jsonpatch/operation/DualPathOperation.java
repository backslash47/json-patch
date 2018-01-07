/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonpatch.operation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatchMessages;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;

import java.io.IOException;

/**
 * Base class for JSON Patch operations taking two JSON Pointers as arguments
 */
public abstract class DualPathOperation
    implements JsonPatchOperation
{
    protected static final MessageBundle BUNDLE
        = MessageBundles.getBundle(JsonPatchMessages.class);

    protected final String op;

    @JsonSerialize(using = ToStringSerializer.class)
    protected final JsonPointer from;

    protected final JsonPointer path;

    /**
     * Protected constructor
     *
     * @param op operation name
     * @param from source path
     * @param path destination path
     */
    protected DualPathOperation(final String op, final JsonPointer from,
        final JsonPointer path)
    {
        this.op = op;
        this.from = from;
        this.path = path;
    }

    @Override
    public final void serialize(final JsonGenerator jgen,
        final SerializerProvider provider)
        throws IOException, JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("op", op);
        jgen.writeStringField("path", path.toString());
        jgen.writeStringField("from", from.toString());
        jgen.writeEndObject();
    }

    @Override
    public final void serializeWithType(final JsonGenerator jgen,
        final SerializerProvider provider, final TypeSerializer typeSer)
        throws IOException, JsonProcessingException
    {
        serialize(jgen, provider);
    }

    @Override
    public final String toString()
    {
        return "op: " + op + "; from: \"" + from + "\"; path: \"" + path + '"';
    }

    public String getOp() {
        return op;
    }

    public JsonPointer getFrom() {
        return from;
    }

    public JsonPointer getPath() {
        return path;
    }
}