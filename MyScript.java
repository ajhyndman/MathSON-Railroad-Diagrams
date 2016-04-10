
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import chrriis.grammar.model.*;
import chrriis.grammar.rrdiagram.*;

public class MyScript {
  public static void main(String [] args) throws IOException {

    // Instantiate converters
    BNFToGrammar grammarGenerator = new BNFToGrammar();
    GrammarToRRDiagram rrDiagramGenerator = new GrammarToRRDiagram();
    RRDiagramToSVG svgGenerator = new RRDiagramToSVG();

    // Read and convert the grammar file.
    File mathSONbnf = new File(".\\MathSON.bnf");
    FileReader mathSONbnfStream = new FileReader(mathSONbnf);
    Grammar mathSONGrammar = grammarGenerator.convert(mathSONbnfStream);

    // Generate SVGs
    Rule[] mathSONRules = mathSONGrammar.getRules();
    ArrayList<RRDiagram> mathSONRRDiagrams = new ArrayList<RRDiagram>();
    for (Rule rule : mathSONRules) {
      mathSONRRDiagrams.add(rrDiagramGenerator.convert(rule));
    }
    ArrayList<String> mathSONSVGs = new ArrayList<String>();
    for (RRDiagram diagram : mathSONRRDiagrams) {
      mathSONSVGs.add(svgGenerator.convert(diagram));
    }

    // Write the SVGs to files.
    int i = 0;
    for (String svgString : mathSONSVGs) {
      String name = ".\\build\\MathSON-Rule-" + i + ".svg";
      File svgFile = new File(name);
      FileWriter svgWriter = new FileWriter(svgFile);
      svgWriter.write(svgString);
      svgWriter.flush();
      svgWriter.close();

      i += 1;
    }

  }
}