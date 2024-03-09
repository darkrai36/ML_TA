package ru.vsu.cs.sibirko_i_s.ML_TA;

import org.apache.commons.cli.*;
import ru.vsu.cs.sibirko_i_s.ML_TA.util.ArrayUtils;
import ru.vsu.cs.sibirko_i_s.ML_TA.util.SwingUtils;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
    public static String defaultFileDirectory = "./Tests/";

    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.ROOT);

            Options options = defaultOptions();

            CmdParams params = parseArgs(options, args);

            if (params.help) {
                String usage = "[-w] or [-i <input-file> (-o <output-file>)]";
                new HelpFormatter().printHelp(usage, options);
                System.exit(0);
            }

            if (params.window) {
                winMain();
            } else if (params.input) {
                int[][] arr = ArrayUtils.readIntArray2FromFile(defaultFileDirectory + params.inputFile);
                double[] result = MatrixSolver.matrixSolver(arr);

                PrintStream out = params.output ?
                        new PrintStream(defaultFileDirectory + params.outputFile) : System.out;
                out.println(ArrayUtils.toString(result));

                out.close();
            } else {
                throw new Exception("No valid options used. Try \"--help\" for more information.");
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }

    public static void winMain() {
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);
        java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));
    }

    public static class CmdParams {
        public boolean help;
        public boolean window;
        public boolean input = false;
        public String inputFile;
        public boolean output = false;
        public String outputFile;
    }

    public static CmdParams parseArgs(Options options, String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        CmdParams params = new CmdParams();
        if (cmd.hasOption("help")) {
            params.help = true;
            return params;
        }
        if (cmd.hasOption("window")) {
            params.window = true;
            return params;
        }
        if (cmd.hasOption("input")) {
            params.input = true;
            params.inputFile = fileNameValidation(cmd.getOptionValue("input"));
        }
        if (cmd.hasOption("output")) {
            params.output = true;
            params.outputFile = fileNameValidation(cmd.getOptionValue("output"));
        }
        return params;
    }

    public static Options defaultOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "print list of options");
        options.addOption("w", "window", false, "launches GUI");
        options.addOption("i", "input", true, "assigns input file");
        options.addOption("o", "output", true, "assigns output file");
        return options;
    }

    public static String fileNameValidation(String fileName) throws Exception {
        fileName = fileName == null ? "" : fileName;

        String extension = "txt";
        String validName;

        Matcher matcher = Pattern.compile("(\\p{Alnum}+).?(\\p{Alpha}*)").matcher(fileName);
        if (matcher.matches()) {
            if (!matcher.group(2).equals(extension)) {
                validName = matcher.group(1) + "." + extension;
            } else {
                validName = fileName;
            }
        } else {
            throw new Exception("Invalid file name");
        }
        return validName;
    }
}