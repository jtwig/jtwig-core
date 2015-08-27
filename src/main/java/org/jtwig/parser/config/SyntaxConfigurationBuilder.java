package org.jtwig.parser.config;

import org.apache.commons.lang3.builder.Builder;

public class SyntaxConfigurationBuilder implements Builder<SyntaxConfiguration> {
    public static SyntaxConfigurationBuilder syntaxConfiguration () {
        return new SyntaxConfigurationBuilder();
    }

    private String startComment = "{#";
    private String endComment = "#}";
    private String startOutput = "{{";
    private String endOutput = "}}";
    private String startCode = "{%";
    private String endCode = "%}";

    private SyntaxConfigurationBuilder() {
    }

    public SyntaxConfigurationBuilder withStartComment(String startComment) {
        this.startComment = startComment;
        return this;
    }

    public SyntaxConfigurationBuilder withEndComment(String endComment) {
        this.endComment = endComment;
        return this;
    }

    public SyntaxConfigurationBuilder withStartOutput(String startOutput) {
        this.startOutput = startOutput;
        return this;
    }

    public SyntaxConfigurationBuilder withEndOutput(String endOutput) {
        this.endOutput = endOutput;
        return this;
    }

    public SyntaxConfigurationBuilder withStartCode(String startCode) {
        this.startCode = startCode;
        return this;
    }

    public SyntaxConfigurationBuilder withEndCode(String endCode) {
        this.endCode = endCode;
        return this;
    }

    @Override
    public SyntaxConfiguration build() {
        return new GeneratedSyntaxConfiguration(
                startComment,
                endComment,
                startOutput,
                endOutput,
                startCode,
                endCode
        );
    }

    private static class GeneratedSyntaxConfiguration implements SyntaxConfiguration {
        private final String startComment;
        private final String endComment;
        private final String startOutput;
        private final String endOutput;
        private final String startCode;
        private final String endCode;

        public GeneratedSyntaxConfiguration(String startComment, String endComment, String startOutput, String endOutput, String startCode, String endCode) {
            this.startComment = startComment;
            this.endComment = endComment;
            this.startOutput = startOutput;
            this.endOutput = endOutput;
            this.startCode = startCode;
            this.endCode = endCode;
        }

        @Override
        public String startComment() {
            return startComment;
        }

        @Override
        public String endComment() {
            return endComment;
        }

        @Override
        public String startOutput() {
            return startOutput;
        }

        @Override
        public String endOutput() {
            return endOutput;
        }

        @Override
        public String startCode() {
            return startCode;
        }

        @Override
        public String endCode() {
            return endCode;
        }
    }
}
