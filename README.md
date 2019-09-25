# login-usercrud
A login system with sessionfilter and user/phone crud, created with Maven, JSF, JPA and Hibernate.

The live app is on heroku at https://calm-basin-45764.herokuapp.com/login.xhtml (trying to solve a bug with heroku postgresql addon)

To make it run locally you need to configure persistance.xml to your database(i recommend hsqlb) credentials.
After that, you can use Tomcat v8.5 to make it run on http://localhost:8080/br.com.pitang/login.xhtml
you can access the app with "admin@admin.com" as user and "admin123" as password.

----------------//-------------------
Um sistema de login com sessionfilter e um crud para usuarios e seus telefones, criado com Maven, JSF, JPA e Hibernate.

O app está online no heroku https://calm-basin-45764.herokuapp.com/login.xhtml (infelizmente o app está com problemas para conectar ao addon do heroku postgre por isso não está passando da tela de login)

Para que ele rode localmente é necessario configurar o persistence.xml com o db de sua preferencia e adicionar sua respectiva dependencia no pom.xml, ou apenas rode o hsqlb com "jdbc:hsqldb:mem:desafio", depois disso apenas use o Tomcat de sua prefêrencia(eu usei o v8.5) para rodar o app no link http://localhost:8080/br.com.pitang/login.xhtml depois de entrar na página de login apenas use admin@admin.com como usuário e admin123 como senha.
