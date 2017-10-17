package m2.vv.tutorials;

import java.io.IOException;
import java.lang.Throwable;
import javassist.*;

public class App
{
    public static void main(String[] args )
    {
        try {
            //TODO: Your code goes here
            ClassPool pool = ClassPool.getDefault();
            CtClass pointClass = pool.makeClass("Point");
            CtField xField = new CtField(CtClass.intType, "x", pointClass);
            pointClass.addField(xField);
            CtField yField = new CtField(CtClass.intType, "y", pointClass);
            pointClass.addField(yField);
            //Ajout d'un getter
            pointClass.addMethod(CtNewMethod.getter("getX", xField));

            pointClass.addMethod(CtNewMethod.getter("getY", yField));
            pointClass.addMethod(CtNewMethod.setter("setX", xField));
            pointClass.addMethod(CtNewMethod.setter("setY", yField));

            pointClass.addMethod(CtNewMethod.make("public String toString() { return \"Hello\"; }", pointClass));

//            pointClass.writeFile();


            //Other way to do so

//            ClassPool poolb = ClassPool.getDefault();
//            Loader loader = new Loader(poolb);
//            Translator logger = new Translator() {
//                public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
//                    System.out.println("Starting...");
//                }
//
//                public void onLoad(ClassPool classPool, String s) throws NotFoundException, CannotCompileException {
//                    System.out.println("Loading..." + s);
//                }
//            };
//            loader.addTranslator(poolb, logger);
//            poolb.appendClassPath("input/target/classes");
//            loader.run("m2.vv.tutorials.QuotesApp", args);

            //Task 4 : translate methode
            String methode = "public void translate(int a, int b){" +
                    "this.setX(this.getX() + a);" +
                    "this.setY(this.getY() + b);" +
                    "}";
            pointClass.addMethod(CtNewMethod.make(methode, pointClass));
            pointClass.writeFile();

            //Task 5 : modify toString method of the Quote Class
            pool.appendClassPath("input/target/classes");
            CtClass quoteClass = pool.getCtClass("m2.vv.tutorials.Quote");
            CtMethod stringMethod= quoteClass.getDeclaredMethod("toString",null);
            String nouvMethode = "if (text.lenght() > 20){" +
                    "return String.format(\"%s - %s\", author, text.substring(0,18) + \"...\";}" +
                    "return String.format(\"%s - %s\", author, text);";
            stringMethod.setBody(nouvMethode);
            quoteClass.writeFile("m2.vv.tutorials.Quote");

        }

        catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }


    }
}
