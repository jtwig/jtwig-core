package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class InMemoryResourceLoader implements ResourceLoader {
    public static InMemoryResourceLoader.Builder builder () {
        return new InMemoryResourceLoader.Builder();
    }

    private final Map<String, Supplier<InputStream>> inputStreamMap;

    public InMemoryResourceLoader(Map<String, Supplier<InputStream>> inputStreamMap) {
        this.inputStreamMap = inputStreamMap;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        Supplier<InputStream> streamSupplier = inputStreamMap.get(path);
        if (streamSupplier == null) throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
        return streamSupplier.get();
    }

    @Override
    public boolean exists(String path) {
        return inputStreamMap.containsKey(path);
    }

    @Override
    public Optional<URL> toUrl(String path) {
        return Optional.absent();
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<InMemoryResourceLoader> {
        private final Map<String, Supplier<InputStream>> supplierMap = new HashMap<>();

        public Builder withResource (String key, String content) {
            supplierMap.put(key, new StringInputStreamSupplier(content));
            return this;
        }

        @Override
        public InMemoryResourceLoader build() {
            return new InMemoryResourceLoader(supplierMap);
        }
    }

    public static class StringInputStreamSupplier implements Supplier<InputStream> {
        private final String content;

        public StringInputStreamSupplier(String content) {
            this.content = content;
        }

        @Override
        public InputStream get() {
            return new ByteArrayInputStream(content.getBytes());
        }
    }
}
