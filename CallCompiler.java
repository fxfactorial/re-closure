import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static com.google.javascript.jscomp.DiagnosticGroups.LINT_CHECKS;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilerOptions.LanguageMode;
import com.google.javascript.jscomp.CheckLevel;
import com.google.javascript.jscomp.BlackHoleErrorManager;

/**
 * An example of how to call the Closure Compiler programmatically,
 * this is LICENSED AS GPL-3.0.
 *
 * @author edgar.factorial@gmail.com (Edgar Aroutiounian)
 */

public class CallCompiler {

  /**
   * @param code JavaScript source code to compile.
   * @return The compiled version of the code.
   */
  public static String compile(String code) {
    Compiler compiler = new Compiler();
    // shut up compiler
    compiler.setErrorManager(new BlackHoleErrorManager());
    CompilerOptions options = new CompilerOptions();

    // See :
    // closure-compiler/src/com/google/javascript/jscomp/CompilerOptions.java
    // lines 2864-2896
    options.setLanguageIn(LanguageMode.ECMASCRIPT_2015);
    options.setLanguageOut(LanguageMode.ECMASCRIPT5_STRICT);
    options.setWarningLevel(LINT_CHECKS, CheckLevel.OFF);

    CompilationLevel.ADVANCED_OPTIMIZATIONS.setOptionsForCompilationLevel(
        options);

    List<SourceFile> list = null;

    try {
      list = CommandLineRunner.getBuiltinExterns(CompilerOptions.Environment.BROWSER);
    } catch (IOException e) {
      System.out.println("Exception raised");
    }

    list.add(SourceFile.fromCode("input.js", code));
    compiler.compile(new ArrayList<SourceFile>(), list, options);
    return compiler.toSource();
  }

  // public static void main(String[] args) {
  // 	String compiled_code = compile("var a = 1 + 2; console.log(a)");
  // 	System.out.println(compiled_code);
  // }
}
