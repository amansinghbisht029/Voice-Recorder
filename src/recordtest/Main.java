/*
            Author: AMAN SINGH BISHT, NIT SRINAGAR (ECE DEPARTMENT)
 */

package recordtest;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            if (!AudioSystem.isLineSupported(dataInfo)) {
                System.out.println("Not Supported");
            }
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open();
            JOptionPane.showMessageDialog(null, "Hit ok to start recording");
            targetLine.start();
            Thread audioRecorderThread = new Thread() {
                @Override
                public void run() {
                    AudioInputStream recordingStream = new AudioInputStream(targetLine);
                    File outputFile = new File("record.wav");
                    try {
                        AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    System.out.println("Supported recording");
                }

            };
            audioRecorderThread.start();
            JOptionPane.showMessageDialog(null, "Hit ok to stop recoding");
            targetLine.stop();
            targetLine.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
