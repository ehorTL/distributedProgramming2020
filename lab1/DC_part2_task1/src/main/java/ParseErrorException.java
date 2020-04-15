public class ParseErrorException extends Throwable {
    public static final String DOCUMENT_BUILDER_FACTORY_ERROR = "DOCUMENT_BUILDER_FACTORY_ERROR";
    public static final String DOCUMENT_BUILDER_ERROR = "DOCUMENT_BUILDER_ERROR";
    public static final String DOCUMENT_ERROR = "DOCUMENT_ERROR";
    public static final String PARSE_ERROR = "PARSE_ERROR";
    public static final String SCHEMA_ERROR = "SCHEMA_ERROR";
    public static final String TRANSFORM_ERROR = "TRANSFORM_ERROR";

    private String message;

    public ParseErrorException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
