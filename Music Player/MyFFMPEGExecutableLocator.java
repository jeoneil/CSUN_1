package COMP380_MP_Final;

import matador.FFMPEGLocator;

public class MyFFMPEGExecutableLocator extends FFMPEGLocator {


    @Override
    protected String getFFMPEGExecutablePath() {
        return "C://MatadorPlayer3/RUN/ffmpeg";
    }
}
