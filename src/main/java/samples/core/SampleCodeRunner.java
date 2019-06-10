package samples.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SampleCodeRunner {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, 
												  NoSuchMethodException, SecurityException, 
												  IllegalAccessException, IllegalArgumentException, 
												  InvocationTargetException, InterruptedException {
		Set<String> files = new HashSet<>();
        getListOfPackages("src/main/java/", files);
        
        for(String pkg : files) {
        	Class<?>[] classList = getClasses(pkg);
        	
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
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }
    
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}