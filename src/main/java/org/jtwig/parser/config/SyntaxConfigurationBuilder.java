package org.jtwig.parser.config;

import org.apache.commons.lang3.builder.Builder;

public class SyntaxConfigurationBuilder<B extends SyntaxConfigurationBuilder> implements Builder<SyntaxConfiguration> {
    private String startComment;
    private String endComment;
    private String startOutput;
    private String endOutput;
    private String startCode;
    private String endCode;

    public SyntaxConfigurationBuilder () {}
    public SyntaxConfigurationBuilder (SyntaxConfiguration prototype) {
        this.startComment = prototype.getStartComment();
        this.endComment = prototype.getEndComment();
        this.startOutput = prototype.getStartOutput();
        this.endOutput = prototype.getEndOutput();
        this.startCode = prototype.getStartCode();
        this.endCode = prototype.getEndCode();
    }


    public B withStartComment(String startComment) {
        this.startComment = startComment;
        return self();
    }

    public B withEndComment(String endComment) {
        this.endComment = endComment;
        return self();
    }

    public B withStartOutput(String startOutput) {
        this.startOutput = startOutput;
        return self();
    }

    public B withEndOutput(String endOutput) {
        this.endOutput = endOutput;
        return self();
    }

    public B withStartCode(String startCode) {
        this.startCode = startCode;
        return self();
    }

    public B withEndCode(String endCode) {
        this.endCode = endCode;
        return self();
    }

    protected B self() {
        return (B) this;
    }

    @Override
    public SyntaxConfiguration build() {
        return new SyntaxConfiguration(
                startComment,
                endComment,
                startOutput,
                endOutput,
                startCode,
                endCode
        );
    }
}
