java-sblayout
=============
This package contains Java libraries that make my life a bit easier while
developing Java web applications. I have observed that for many web applications
that I have developed in the past, all pages all more or less look and behave in
a quite similar way.

As a consequence, I have found myself writing lots of boiler plate code that
had to be repeated for each additional page I implement. Furthermore, it also
turned maintenance of pages into quite a tedious problem.

The libraries in this package allow someone to define a web application as a set
of pages that refer to other sub pages. Developers only have to capture the
common aspects, such as the sections and style of the entire web application,
once and only need to provide the individual characteristcs of every additional
sub page.

The libraries automatically compose the corresponding pages, and ensures a number
of non-functional quality attributes, such as a mechanism allowing end users to
always know where they are in the navigation structure of the application.

Moreover, it also automatically hides sub pages in the menu sections that are
not accessible.

Building the libraries
======================
The libraries in this package can be built using
[Apache Ant](http://ant.apache.org). The model package resides in the
`LayoutModel/` folder and can be built by running:

    $ cd LayoutModel
    $ ant generate.library.jar

After Ant has finished building, a JAR file named `LayoutModel.jar` has been
generated.

The view package resides in the `LayoutView/` folder and can be built by
running:

    $ cd LayoutView
    $ ant generate library.jar

After Ant has completed the above task, a JAR file named `LayoutView.jar` has
been generated.

Usage
=====
The libraries can be used in a straight forward way. To get a web application
working we have to remember three things.

First, we must create an object instance of the `Application` class that serves
as the _model_ of the application -- it captures common properties such as the
sections, style settings, and all the sub pages of the application.

An application model can be displayed by creating a _view_ page that includes
taglibs displaying an index page or sections of an index page. This package
contains a trivial index taglib `index.tld` that generates a page from an
application model.

Finally, we always use one single address (invoking the view page) that handles
all requests to every sub page. The path components that are appended to its URL
serve as selectors for the subpages of the application. For example:

* `http://localhost/index.wss` refers to the entry page of the web application
* `http://localhost/index.wss/a` refers to a sub page reachable from the entry page
* `http://localhost/index.wss/a/b` refers to a sub page reachable from the previous sub page

Implementing a very trivial web application
-------------------------------------------
To create a very trivial web application displaying one page, we must first
create an index servlet composing a simple application model and dispatches a
view page:

```java
package test;
import io.github.svanderburg.layout.model.*;
import io.github.svanderburg.layout.model.page.*;
import io.github.svanderburg.layout.model.page.content.*;
import io.github.svanderburg.layout.model.section.*;

public class IndexServlet extends io.github.svanderburg.layout.view.IndexServlet
{
    private static final Application application = new Application(
        /* Title */
        "Trivial web application",
        
        /* CSS stylesheets */
        new String[] { "default.css" },
        
        /* Pages */
        new StaticContentPage("Fruit", new Contents("fruit.jsp"))
    )
    /* Sections */
    .addSection("header", new StaticSection("header.jsp"))
    .addSection("contents", new ContentsSection(true))
    .addSection("footer", new StaticSection("footer.jsp"));
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatchLayoutView(application, req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        dispatchLayoutView(application, req, resp);
    }
}
```

In the above code fragement, we compose an application model in which every sub
page consists of three sections. The `header` and `footer` always display the
same code fragment. The content section is filled with variable text that makes
every page unique.

Every sub page has `Trivial web application` in the title and use the style
settings from the `default.css` stylesheet.

The view page should reside in `WEB-INF/index.jsp`. A simple variant can be
written as follows:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="io.github.svanderburg.layout.model.*,io.github.svanderburg.layout.model.page.*, test.*"%>
<%
Application app = (Application)request.getAttribute("app");
Page currentPage = (Page)request.getAttribute("currentPage");
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>
<layout:index app="<%= app %>" currentPage="<%= currentPage %>" />
```

The above code fragment retrieves the application model and requested page. Then
it includes the `index` taglib to compose a page with a trivial structure from
it.

Each section in the model translates to `div` elements with their `id` attribute
set to their corresponding array key in the model).

We need the create the following `web.xml` file to make sure that the 
`IndexServlet` can be used and that the error pages are mapped accordingly:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
  <servlet>
    <servlet-name>index</servlet-name>
    <servlet-class>test.IndexServlet</servlet-class>
  </servlet>
  <servlet>
    <servlet-name>indexjsp</servlet-name>
    <jsp-file>/WEB-INF/index.jsp</jsp-file>
  </servlet>
  <servlet-mapping>
    <servlet-name>index</servlet-name>
    <url-pattern>/index.wss/*</url-pattern>
  </servlet-mapping>
  <welcome-file-list>
    <welcome-file>index.wss</welcome-file>
  </welcome-file-list>
  <error-page>
    <error-code>403</error-code>
    <location>/index.wss/403</location>
  </error-page>
  <error-page>
    <error-code>404</error-code>
    <location>/index.wss/404</location>
  </error-page>
</web-app>
```

After creating the model and view, we must implement the code for the static
sections and sub pages. The above model expects a WAR file with the following
directory structure that looks as follows:

    styles/
      default.css
    WEB-INF/
      classes/
        test/
          IndexServlet.class
      lib/
        LayoutModel.jar
        LayoutView.jar
      sections/
        header.jsp
        footer.jsp
      contents/
        fruit.jsp
      index.jsp
      web.xml

The files to which the `StaticSection` objects refer should reside in
`WEB-INF/sections/`, stylesheets should reside in `styles/` and the contents of every sub
page should reside in `WEB-INF/contents/`.

Besides the pages and stylesheets, we must also bundle the libraries and the
compiled servlet class. To conveniently compose a WAR file, we can use the
following Apache Ant recipe as a template:

```xml
<project name="Fruit" basedir="." default="generate.war">
    <property name="build.dir" value="build" />
    <property name="deploybuild.dir" value="${build.dir}/classes" />

    <!-- Imports environment variables as properties -->
    <property environment="env" />
        <condition property="TOMCAT_LIB" value="${env.TOMCAT_LIB}" else="../apache-tomcat">
        <isset property="env.TOMCAT_LIB" />
    </condition>

    <condition property="LAYOUT_MODEL_LIB" value="${env.LAYOUT_MODEL_LIB}" else="../LayoutModel">
        <isset property="env.LAYOUT_MODEL_LIB" />
    </condition>

    <condition property="LAYOUT_VIEW_LIB" value="${env.LAYOUT_VIEW_LIB}" else="../LayoutView">
        <isset property="env.LAYOUT_VIEW_LIB" />
    </condition>

    <target name="copy.libraries">
        <copy toDir="${basedir}/WebContent/WEB-INF/lib">
            <fileset dir="${LAYOUT_MODEL_LIB}" includes="*.jar" />
        </copy>
        <copy toDir="${basedir}/WebContent/WEB-INF/lib">
            <fileset dir="${LAYOUT_VIEW_LIB}" includes="*.jar" />
        </copy>
    </target>
        
    <!-- Sets the classpath which is used by the Java compiler -->
    <path id="service.classpath">
        <fileset dir="${TOMCAT_LIB}">
            <include name="*.jar" />
        </fileset>
    
        <fileset dir="${LAYOUT_MODEL_LIB}">
            <include name="*.jar" />
        </fileset>
        
        <fileset dir="${LAYOUT_VIEW_LIB}">
            <include name="*.jar" />
        </fileset>
    </path>
    
    <target name="compile" depends="copy.libraries">
        <mkdir dir="${deploybuild.dir}" />
            
        <javac debug="on"
               fork="true"
               destdir="${deploybuild.dir}"
               srcdir="${basedir}/src"
               classpathref="service.classpath"/>
    </target>

    <target name="generate.war" depends="compile">
        <war destfile="Fruit.war" basedir="${basedir}">
            <classes dir="build/classes" includes="**" />
            <fileset dir="WebContent" includes="**" />
        </war>
    </target>

    <target name="clean">
        <delete file="${basedir}/Fruit.war" />
        <delete>
            <fileset dir="${basedir}/WebContent/WEB-INF/lib" includes="*.jar" />
        </delete>
        <delete dir="${deploybuild.dir}" quiet="true" />
        <delete dir="${build.dir}" quiet="true" />
    </target>
</project>
```

The above Ant file defines several targets:

* `copy.libraries` copies the libraries to the working directory so that they can
be found during building and packaged into the WAR file. The `LAYOUT_MODEL_LIB`
and `LAYOUT_VIEW_LIB` environment variables can be used to configure their exact
locations

* The `compile` target is responsible for compiling all Java source files

* The `generate.war` target is the default target and assembles a Web application
ARchive (WAR) from all artifacts required to run the web application.

* The `clean` target removes all artifacts that are produced by the build
targets.

Running the following command-line instruction:

    $ ant generate.war

produces the WAR file that we can deploy to a Servlet container, such as
[Apache Tomcat](http://tomcat.apache.org).

Implementing a web application with sub pages
---------------------------------------------
We can adapt the page parameter (in the application model shown earlier) to refer
to a collection of sub pages by adding an additional parameter to the
constructor. Each element in the array represents a sub page displaying a
specific kind of fruit:

```java
/* Pages */
new StaticContentPage("Fruit", new Contents("fruit.jsp"))
    .addSubPage("apples", new StaticContentPage("Apples", new Contents("fruit/apples.jsp")))
    .addSubPage("pears", new StaticContentPage("Pears", new Contents("fruit/pears.jsp")))
    .addSubPage("oranges", new StaticContentPage("Oranges", new Contents("fruit/oranges.jsp"))),
```

By adding a menu section, we can automatically show a menu section on every page
that displays links to their sub pages and marks the link that is currently
selected as such. Do that we must change the section operations to include a menu
section:

```java
/* Sections */
.addSection("header", new StaticSection("header.jsp"))
.addSection("menu", new MenuSection(0))
.addSection("contents", new ContentsSection(true))
.addSection("footer", new StaticSection("footer.jsp"));
```

We must also add a couple of additional files that display the contents of each
sub page:

    WEB-INF/
      contents/
        fruit/
          apples.jsp
          pears.jsp
          oranges.jsp

After making these modifications, each page shows a menu section that displays
the fruit kinds. Clicking on a link will redirect us to the page displaying it.

Moreover, the URL component that comes after `index.wss` also allows us to
navigate to every fruit flavour. For example, the following URL redirects us to
the oranges sub page: `http://localhost/index.wss/oranges`

Implementing more complex navigation structures
-----------------------------------------------
It is also possible to have multiple levels of subpages. For example, we can also
add sub pages to sub pages and an additional menu section (`submenu`) displaying
the available sub sub pages per sub page:

```java
/* Sections */
.addSection("header", new StaticSection("header.jsp"))
.addSection("menu", new MenuSection(0))
.addSection("submenu", new MenuSection(1))
.addSection("contents", new ContentsSection(true))
.addSection("footer", new StaticSection("footer.jsp"));
```

and modify the sub page to include sub sub pages:

```java
/* Pages */
new StaticContentPage("Fruit", new Contents("fruit.jsp"))
    .addSubPage("apples", new StaticContentPage("Apples", new Contents("fruit/apples.jsp"))
        .addSubPage("red", StaticContentPage("Red", new Contents("fruit/apples/red.jsp")))
        .addSubPage("green", StaticContentPage("Green", new Contents("fruit/apples/green.jsp"))))
    .addSubPage("pears", new StaticContentPage("Pears", new Contents("fruit/pears.jsp"))
        .addSubPage("yellow", StaticContentPage("Yellow", new Contents("fruit/pears/yellow.jsp")))
        .addSubPage("green", StaticContentPage("Green", new Contents("fruit/pears/green.jsp"))))
    .addSubPage("oranges", new StaticContentPage("Oranges", new Contents("fruit/oranges.jsp"))
        .addSubPage("orange", StaticContentPage("Orange", new Contents("fruit/oranges/orange.jsp")))
        .addSubPage("yellow", StaticContentPage("Yellow", new Contents("fruit/oranges/yellow.jsp")))),
```

Similar to the previous example, a submenu section displays the subpages of a
particular fruit kind.

We can also use the URL to get to a specific sub sub page. For example, the
following URL shows the red apple sub sub page:
`http://localhost/index.wss/apples/red`.

You can nest sub pages as deep as you want, but for the sake of usability this is
not recommended in most cases.

Error pages
-----------
It may also happen that some error occurs while trying to display a page. For
example, trying to access a subpage that does not exists (e.g.
`http://localhost/index.wss/oranges/purple`) should display a 404 error page.
Moreover, pages that are inaccessible should display a 403 error page.

These error pages can be defined by adding them as a sub page to the entry page
with keys `403` and `404`:

```java
/* Pages */
new StaticContentPage("Fruit", new Contents("fruit.jsp"))
    .addSubPage("403", new StaticContentPage("Forbidden", new Contents("error/403.jsp")))
    .addSubPage("404", new StaticContentPage("Page not found", new Contents("error/404.jsp")))
    ...
```

Security handling
-----------------
If it is desired to secure a page from unauthorized access, you can implement
your own class that inherits from `Page` which overrides the
`checkAccessibility()` method. This function should return `true` if and only if
an end user is authorized to view it.

For example, the following class implements a page displaying content that denies
access to everyone:

```java
package test;
import io.github.svanderburg.layout.model.page.*;
import io.github.svanderburg.layout.model.page.content.*;

public class InaccessibleContentPage extends ContentPage
{
    public InaccessibleContentPage(String title, Contents contents)
    {
        super(title, contents);
    }

    @Override
    public boolean checkAccessibility()
    {
        return false;
    }
}
```

You can do in the body of `checkAccessibility()` whatever you want. For example,
you can also change it to take some cookie values containing a username and
password that gets verified against something that is stored in a database.

By adding an object that is in instance of our custom class to a subpage of the
entry page, we can secure it.

Implementing more complex dynamic layouts
-----------------------------------------
We can also support more complex dynamic layouts. In our previous example with
fruit kinds, we only defined one content section in which details about the fruit
kind is displayed.

We can also change the application model to have two dynamic content sections
(or even more). By removing the default string parameter to the `Contents`
constructor and using fluent interfaces, we can specify the contents of each
content section of page.

The following model makes the header as well as the contents sections dynamic for
each sub page with the following sections:

```java
/* Sections */
.addSection("header", new StaticSection("header.jsp"))
.addSection("menu", new MenuSection(0))
.addSection("contents", new ContentsSection(true))
.addSection("footer", new StaticSection("footer.jsp"));
```

To make more than one section dynamic, we can define the pages as follows:

```java
/* Pages */
new StaticContentPage("Fruit", new Contents()
    .addSection("header", "fruit.jsp")
    .addSection("contents", "fruit.jsp"))
    .addSubPage("apples", new StaticContentPage("Apples", new Contents()
        .addSection("header", "fruit/apples.jsp")
        .addSection("contents", "fruit/apples.jsp")))
    .addSubPage("pears", new StaticContentPage("Pears", new Contents()
        .addSection("header", "fruit/pears.jsp")
        .addSection("contents", "fruit/pears.jsp")))
    .addSubPage("oranges", new StaticContentPage("Oranges", new Contents()
        .addSection("header", "fruit/oranges.jsp")
        .addSection("contents", "fruit/oranges.jsp")))
```

The above model also requires a few additional files that should reside in the
`header` subdirectory inside `WEB-INF`:

    WEB-INF/
      header/
        fruit.jsp
        fruit/
          apples.jsp
          pears.jsp
          oranges.jsp

The above files should display the header for each fruit kind.

Handling GET or POST parameters
-------------------------------
Sometimes it may also be required to process GET or POST parameters, if a sub
page (for example) contains a form.

The contents object can also take a controller parameter that invokes a page that
is processed before any HTML output is rendered:

```java
/* Pages */

new StaticContentPage("Fruit", new Contents("fruit.jsp"))
    ...
    .addSubPage("question", new StaticContentPage("Question", new Contents("question.jsp", "/question.wss"))),
    ...
)
```

The above code fragment adds a sub page that displays a form asking the user a
question what his/her favorite fruit kind is. After a user submits his answer
through the form the same page is displayed. Instead of showing the form the
answer is displayed.

The page provided as second parameter to the `Contents` constructor takes
care of processing the POST parameter. It can both be a Servlet or a JSP page.

Using path components as parameters
-----------------------------------
Instead of using the path components in a URL to address sub pages, we may also
want to use path components as parameters instead. To use path components as
parameters, we can use objects that are instance of `DynamicContentPage`.

The following code fragments adds a sub page having a sub page that interprets
the a component:

```java
/* Pages */

new StaticContentPage("Fruit", new Contents("fruit.jsp"))
    ...
    .addSubPage("fruitname", new DynamicContentPage("Display fruit name", "fruitname", new Contents("fruitname.jsp")))
    ...
)
```

The first parameter of the constructor contains the title, the second the name of
the variable that will be set when the sub page is processed, and the third
parameter configures the content sections that are supposed to interpret the
variable.

We can implement the `fruitname.jsp` to simply display the parameter:

```jsp
<%@ page import="java.util.*" %>
<%
HashMap<String, String> query = (HashMap<String, String>)request.getAttribute("query");
%>
<%= query.get("fruitname") %>
```

If we address the page with: `http://localhost/index.wss/fruitname/apples` we
should see:

    apples

and if we address the page with: `http://localhost/index.wss/fruitname/bananas`
we should see:

    bananas
    
The `DynamicContentPage` constructor also has an optional fourth parameter to
define additional sub pages or to interpret multiple parameters.

Implementing an internationalised web application
-------------------------------------------------
Another use case is implementing internationalised web applications. By creating
a page that is an instance of a `LocalizedContentPage` we can easily support
the same page in multiple languages:

```java
/* Pages */
new LocalizedContentPage()
    .addSubPage("nl", new StaticContentPage("Nederlands", new Contents("nl.jsp")))
    .addSubPage("en-us", new StaticContentPage("American", new Contents("en-us.jsp")))
    .addSubPage("en-gb", new StaticContentPage("British", new Contents("en-gb.jsp")))
    .addSubPage("fr", new StaticContentPage("Français", new Contents("fr.jsp")))
    .addSubPage("de", new StaticContentPage("Deutsch", new Contents("de.jsp")))
```

The above code fragment defines a page with translations into Dutch, American,
British, French and German.

Any user can retrieve a particular translation of a page (such as German) by
using the following URL:

    http://localhost/index.wss/de

If the root of this URL is used:

    http://localhost/index.wss

Then the preferred language will be derived from the `Accept-Language` parameter
in the HTTP header that is sent by the user agent.

If a particular variant of language is not supported (e.g. the Belgian variant of
Dutch: `nl-be`) then the detection algorithm will automatically do a fallback to
the generic variant: `nl`.

If none of the preferred languages is supported, the first option in the array
will be taken (which is `nl` in our example).

More use cases
--------------
There are also facilities to include application wide and per-page stylesheets
and script includes. We can also make pages invisible from menu sections by
instantiating pages that are prefixed with `Hidden*`.

Consult the API documentation for more information.

Examples
========
This package includes three example web applications that can be found in the
`examples/` folder:

* The `Simple` web application demonstrates simple sub pages, inaccessible sub pages, dynamic sub pages and a page handling POST requests
* The `I18N` web application demonstrates an internationalised web page displaying the same page in multiple languages
* The `Advanced` web applications demonstraties more advanced sub pages with multiple content sections. It also demonstrates style and script variability.

API documentation
=================
This package includes API documentation, which can be generated with Javadoc. The
Ant file in both library projects package contain a target named `doc` target and
produce the corresponding HTML files in a folder called `doc/`:

    $ ant doc

License
=======
The contents of this package is available under the [Apache Software License](http://www.apache.org/licenses/LICENSE-2.0.html)
version 2.0
