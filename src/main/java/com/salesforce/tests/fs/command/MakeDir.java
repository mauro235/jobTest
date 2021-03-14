package com.salesforce.tests.fs.command;

import com.salesforce.tests.fs.filesystem.Filesystem;

import java.util.List;

public class MakeDir extends CommandImpl {
    public static String command = "mkdir";

    private Filesystem fs;

    public MakeDir(Filesystem fs) {
        this.fs = fs;
    }

    // args: dirName
    // err: 'Directory already exists'
    @Override
    public String parse(String cmd, List<String> args) {
        String response = "Usage: " + command + " dirName";

        if (cmd.equals(command)) {
            if (args != null && args.size() == 2) {
                String filename = args.get(1);
                if (filename != null && filename.matches("[a-zA-Z0-9]+")) {
                    if (fs != null) {
                        if (!fs.nameExists(filename)) {
                            fs.createDirectory(filename);
                            return "";
                        } else {
                            return "File/Directory " + filename + " already exists";
                        }
                    }
                } else {
                    return "Invalid dirName";
                }
            }

        } else {
            if (nextCommand != null) {
                return nextCommand.parse(cmd, args);
            } else {
                return emtpyChainError(cmd);
            }
        }

        return response;
    }

}
