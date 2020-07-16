Format your gql queries

Language Supported 
1. Kotlin

What this tool will do - 
1. It will reformat your GQL query that is stored in some variable (The idea is very similar to JSON Formatter)
2. You can then replace this formatted GQL query into that variable 

How to use -
1. Open the tool window from RIGHT ![screen](../master/screens/src_editor.png)
2. Navigate to your kotlin file where you have written your GQL queries
3. Enter the variable name which you want to reformat
4. Click Import You will see a readable version of that GQL query stored in that variable ![screen](../master/screens/src_import.png)
6. Now you can also edit your GQL queries in this tool window
7. Click Replace to finally your replace your edited text from tool window to your variable's value ![screen](../master/screens/src_replace.png)

Few things to note - 

Dollar replace logic
1. $ will be converted to ${"$"}
2. $$ and ${"$"} will not be changed


Video - https://youtu.be/zP1y7HndSlM

Feel free to request features / open issues

Thanks