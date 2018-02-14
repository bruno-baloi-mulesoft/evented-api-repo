package org.mule.modules.eventedapi.parser;

/**
  * User Exception used to exemplify how to handle amf exception.
 */
public class ParserException extends Exception {
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}