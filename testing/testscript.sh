copy /Y ..\src\*.java .\
javac Main.java
java Main < input.txt > actual.txt
del *.class
diff actual.txt expected.txt
