package com.salesforce.tests.fs.filesystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filesystem {
    public static final String PATH_DELIMITER = "/";
    private static final String BASE_PATH = PATH_DELIMITER;
    private static final String ROOT_PATH = "root";
    private static final String PERSISTANCE_FILENAME = "filesystem.txt";

    // FS Structure
    private Node currentNode;
    private Node rootNode = null;
    private boolean persistFilesystem = false;

    public Filesystem(boolean persist) {

        persistFilesystem = persist;

        if (persistFilesystem) {
            try {
                FileInputStream fi = new FileInputStream(new File(PERSISTANCE_FILENAME));
                ObjectInputStream inputStream = new ObjectInputStream(fi);

                rootNode = (Node) inputStream.readObject();

                inputStream.close();
                fi.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        // Current path initialize
        if (rootNode == null) {
            rootNode = new Node(ROOT_PATH, Node.FileType.DIRECTORY);
        }

        //System.out.println("Root node name: " + rootNode.name);

        currentNode = rootNode;
    }

    private void persistFilesystemState() {
        if (persistFilesystem) {
            // Save FS
            try {
                FileOutputStream fo = new FileOutputStream(new File(PERSISTANCE_FILENAME));
                ObjectOutputStream outputStream = new ObjectOutputStream(fo);

                if (outputStream != null) {
                    outputStream.writeObject(rootNode);
                }
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }

    public String getCurrentPath() {
        if (currentNode != null) {
            return currentNode.getPath();
        } else {
            return BASE_PATH + ROOT_PATH;
        }
    }

    public String getPathContents(String path, boolean recursive) {
        if (path != null) {
            // Should parse provided path and check validity
            return "";
        } else {
            // Current path contents
            return currentNode.getChildList(false);
        }
    }

    public void createDirectory(String name) {
        Node dir = new Node(name, Node.FileType.DIRECTORY);
        currentNode.appendNode(dir);

        persistFilesystemState();
    }

    public void createFile(String name) {
        Node file = new Node(name, Node.FileType.FILE);
        currentNode.appendNode(file);

        persistFilesystemState();
    }

    public String changePath(String path) {
        String response = "Invalid path, no such directory named " + path;

        // Special cases "." and ".."
        if (path.equals(".")) {
            // Break early
            return "";
        }

        if (path.equals("..")) {
            if (currentNode.hasParent()) {
                currentNode = currentNode.getParent();
                return "";
            }
        }

        // Path parsing
        List<String> pathSegments = new ArrayList(Arrays.asList(path.split(Filesystem.PATH_DELIMITER)));

        Node initialNode = currentNode;

        if (pathSegments.size() > 0) {
            // Check from root or relative to current dir
            if (pathSegments.get(0).equals("")) {
                if (pathSegments.get(1) != null && pathSegments.get(1).equals(ROOT_PATH)) {
                    currentNode = rootNode;
                    pathSegments.remove(0);
                    pathSegments.remove(ROOT_PATH);
                }
            }

            int i = pathSegments.size();

            for (String p : pathSegments) {
                response = "Invalid path " + path;
                i--;

                if (currentNode.getChilds() != null && currentNode.getChilds().size() > 0) {
                    for (Node n : currentNode.getChilds()) {
                        if (p.equals(n.name) && n.type == Node.FileType.DIRECTORY) {
                            currentNode = n;
                            response = "";
                            break;
                        }
                    }
                }

                if (response.length() > 0) {
                    currentNode = initialNode;
                    break;
                }
            }
        }

        return response;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public boolean nameExists(String name) {
        boolean result = false;

        // Break early
        if (name.equals(ROOT_PATH) || name.equals(PATH_DELIMITER)) {
            return true;
        }

        if (currentNode.getChilds() != null) {
            for (Node n : currentNode.getChilds()) {
                if (n.name.equals(name)) {
                    result = true;
                }
            }
        }

        return result;
    }

}
