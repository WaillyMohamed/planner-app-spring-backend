package entity;

import java.time.LocalTime;

public record TimeBlock (LocalTime start, LocalTime end) {}
// A helper class
// A temporary slot where a session might fit.
// Created during scheduling and used for calculations, then discarded.