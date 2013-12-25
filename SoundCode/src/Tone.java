

import java.util.*;
import javax.sound.sampled.*;

public class Tone implements Bit{
     public static float SAMPLE_RATE;
     protected static int hz;
     protected static int msecs;
     protected double vol;
     
     
     public Tone(double vol){
 	 
    	 this.vol = vol;
     }

     public  void play()
      throws LineUnavailableException {
    	 
    	 
         if (hz <= 0)
             throw new IllegalArgumentException("Frequency <= 0 hz");

         if (msecs <= 0)
             throw new IllegalArgumentException("Duration <= 0 msecs");

         if (vol > 1.0 || vol < 0.0)
             throw new IllegalArgumentException("Volume out of range 0.0 - 1.0");

         byte[] buf = new byte[(int)SAMPLE_RATE * msecs / 1000];

         for (int i=0; i<buf.length; i++) {
             double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
             buf[i] = (byte)(Math.sin(angle) * 127.0 * vol);
         }

         // shape the front and back 10ms of the wave form
         for (int i=0; i < SAMPLE_RATE / 100.0 && i < buf.length / 2; i++) {
             buf[i] = (byte)(buf[i] * i / (SAMPLE_RATE / 100.0));
             buf[buf.length-1-i] =
              (byte)(buf[buf.length-1-i] * i / (SAMPLE_RATE / 100.0));
         }

         AudioFormat af = new AudioFormat(SAMPLE_RATE,8,1,true,false);
         SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
         sdl.open(af);
         sdl.start();
         sdl.write(buf,0,buf.length);
         sdl.drain();
         sdl.close();
     }

	public int getHz() {
		return hz;
	}

	public static void setHz(int hz) {
		Tone.hz = hz;
	}

	public static int getMsecs() {
		return msecs;
	}

	public static void setMsecs(int msecs) {
		Tone.msecs = msecs;
	}

	public double getVol() {
		return vol;
	}

	public  void setVol(double vol) {
		this.vol = vol;
	}

     
     
     
}
