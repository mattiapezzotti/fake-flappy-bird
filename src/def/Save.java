package def;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Save implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum counterType {
        count_down,
        count_up
    }
    public int myIntValue = 0;

    public static void saveData(Double o) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
    
        try {
            fos = new FileOutputStream("save.ser");
            out = new ObjectOutputStream(fos);
            out.writeObject(o);

            out.close();
        } catch (Exception ex) {
           // ex.printStackTrace();
        }
    }

    public static void resetFiles() {
        {
            {
                File file = new File("save.ser");

                if (file.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
            }
        }
    }

    public static double loadData() {
    	double g = 0;
        FileInputStream fis = null;
        ObjectInputStream in = null;
       try {
            fis = new FileInputStream("save.ser");
            in = new ObjectInputStream(fis);
            g = (double) in.readObject();
            in.close();
        } catch (Exception ex) {
           // ex.printStackTrace();
        }
        return g;
    }
}


