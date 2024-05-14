package org.example.vehicle.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StringAdapter {
    private final OutputStream outputStream;

    public StringAdapter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String[] strings) throws IOException {
        for (String s : strings) {
            this.outputStream.write(s.getBytes(StandardCharsets.UTF_8));
            this.outputStream.write(32);
        }
    }
}
