package uk.ac.sussex.group6.backend.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LongLatCoordinates {

    private ArrayList<String> longlats;

}
