package com.RD.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew 2 on 18/02/2019.
 */
public class SlashMode extends ModeTemplate{
    public SlashMode(Frame frame) {
        super(frame);
        ArrayList<MenuItem> slashModeOptions = new ArrayList<MenuItem>(Arrays.asList(
               // new MenuItem()
        ));

        setOptions(slashModeOptions);
    }


}
