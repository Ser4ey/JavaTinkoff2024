package edu.java.bot.exception.service;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UnknownException extends RuntimeException{
}
