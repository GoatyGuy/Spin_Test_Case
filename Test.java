package my.tests;

import org.camunda.spin.Spin;
import org.camunda.spin.xml.SpinXPathException;
import org.camunda.spin.xml.SpinXmlElement;

/**
 * Test, to show a potential bug in Spin.
 */
public class Test {

    /**
     * Test.
     * @param args argumente
     */
    public static void main(final String[] args) {
        final SpinXmlElement root = Spin.XML("<root xmlns=\"test\"><sub></sub></root>");
        System.out.println("<sub> is automatically in the same namespace like <root>");
        final SpinXmlElement funzt1 = root.xPath("./ns:sub").ns("ns", "test").element();
        System.out.println(funzt1.toString());

        System.out.println("--------------------------------");

        System.out.println("An element is attached. It should be also in the same namespace like <sub>, right?");
        final SpinXmlElement sub2 = Spin.XML("<sub2></sub2>");
        root.append(sub2);

        System.out.println("Nope! SpinXpathException...");
        try {
            final SpinXmlElement error1 = root.xPath("./ns:sub2").ns("ns", "test").element();
            System.out.println(error1.toString());
        } catch (final SpinXPathException ex) {
            System.out.println("<sub2> has no namespace!");
        }

        System.out.println("--------------------------------");

        System.out.println("Proof:");
        final SpinXmlElement funzt2 = root.xPath("./sub2").element();
        System.out.println(funzt2.toString());

        System.out.println("--------------------------------");

        System.out.println("Now <root> looks like this\n"
        + "The written namespace for <sub2> is xmlns=\"\"! Remember that later!!!");
        System.out.println(root.toString());

        System.out.println("--------------------------------");

        System.out.println("Let´s add a third <sub>-element. This time with the same namespace as its parent.");
        final SpinXmlElement sub3 = Spin.XML("<sub3 xmlns=\"test\"></sub3>");
        root.append(sub3);

        System.out.println("WHAAAT??? It´s also xmlns=\"\"????");
        System.out.println(root.toString());

        System.out.println("--------------------------------");

        System.out.println("According to Cocker the following lines must work...\n"
        + "Because both elements have xmlns=\"\"");
        try {
            final SpinXmlElement error2 = root.xPath("./sub3").element();
            System.out.println(error2.toString());
        } catch (final SpinXPathException ex) {
            System.out.println("Nope, not today! <sub3> has a namespace!");
        }

        System.out.println("--------------------------------");

        System.out.println("Now I don´t understand nothing at all...");
        final SpinXmlElement funzt3 = root.xPath("./ns:sub3").ns("ns", "test").element();
        System.out.println(funzt3.toString());

        System.out.println("--------------------------------");

        System.out.println("In the end, the complete XML:");
        System.out.println(root.toString());

        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
    }
}
