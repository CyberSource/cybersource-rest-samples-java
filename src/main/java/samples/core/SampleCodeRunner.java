package samples.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.google.common.reflect.ClassPath;

public class SampleCodeRunner {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, 
												  NoSuchMethodException, SecurityException, 
												  IllegalAccessException, IllegalArgumentException, 
												  InvocationTargetException, InterruptedException {
		Set<String> files = new HashSet<>();
		// System.out.println(System.getProperty("user.dir"));
		
		getListOfPackages("src/main/java/", files);
        
		for(String pkg : files) {
        	Class<?>[] classList = getClasses(pkg);
        	// System.out.println(pkg + " : " + Arrays.toString(classList));
        	
        	if (classList.length > 0) {
        		for(Class<?> sampleClass : classList) {
        			// IGNORE LIST PART 1 : Classes inside Data, lib and SampleCodeRunner packages are not tested.
        			if (sampleClass.getName().contains("Data") || sampleClass.getName().contains("lib") || sampleClass.getName().contains("SampleCodeRunner")) {
        				System.out.println("\n#### SKIPPED - " + sampleClass.getName() + " ####");            			
            			continue;
        			}
        			
        			Method sample;
					try {
						sample = sampleClass.getDeclaredMethod("main", String[].class);
					} catch (Exception e) {
						// IGNORE LIST PART 2 : Classes without a main() method are not runnable.
						System.out.println("\n#### SKIPPED - " + sampleClass.getName() + " ####");
						continue;
					}
					
        			final Object[] methodArgs = new Object[1];
        			methodArgs[0] = new String[] {};
        			
        			try {            			
            			System.out.println("\n**** RUNNING - " + sampleClass.getName() + " ****");
            			
            			sample.invoke(null, methodArgs);
            			
            			Thread.sleep(3000);
            			
            			System.out.println("\n\n**** ENDING - " + sampleClass.getName() + " ****");            			
					} 
        			catch (Exception e) {
            			System.out.println("\n\n**** ENDING - " + sampleClass.getName() + " ****");
						continue;
					}
        		}
        	}
        }
    }

	/**
     * Recursively extract the names of all packages from the given path
     *
     * @param directoryName Path in which to extract packages
     * @param pack List of all packages
     */
	public static void getListOfPackages(String directoryName, Set<String> pack) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                String path = file.getPath();
                String packName = path.substring(path.indexOf("src") + 4, path.lastIndexOf('\\')).replace("main\\java\\", "");
                pack.add(packName.replace('\\', '.'));
            } else if (file.isDirectory()) {
                getListOfPackages(file.getAbsolutePath(), pack);
            }
        }
    }
    
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        // String path = packageName.replace('.', '/');
        // System.out.println("PATH : " + path);
        
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        ClassPath cp = ClassPath.from(Thread.currentThread().getContextClassLoader());
        for(ClassPath.ClassInfo info : cp.getTopLevelClassesRecursive(packageName)) {
            classes.add(info.load());
        }
        
        return classes.toArray(new Class[classes.size()]);
    }
}