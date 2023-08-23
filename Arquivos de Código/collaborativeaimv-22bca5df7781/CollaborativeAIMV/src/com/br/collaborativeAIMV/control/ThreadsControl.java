package com.br.collaborativeAIMV.control;

import org.eclipse.swt.widgets.Display;

public class ThreadsControl {
	public static void runThread(Runnable runnable){
		Display.getDefault().syncExec(wrapSafe(runnable));
	}
	
	public static Runnable wrapSafe(
	        final Runnable runnable) {

	        return new Runnable() {
	            public void run() {
	                try {
	                	runnable.run();
	                } catch (RuntimeException e) {
	                } catch (Error e) {
	                    // Re-throw errors (such as an OutOfMemoryError)
	                    throw e;
	                }
	            }
	        };
	    }
	
	public static void runThread(Thread thread){
		thread.start();
	}
	
}
