with open("items_output.txt", "r") as f:
    items_code = f.read().strip()

# remove trailing comma if it exists
if items_code.endswith(","):
    items_code = items_code[:-1]

with open("app/src/main/java/com/example/sortsmart_se/model/WasteItem.kt", "r") as f:
    code = f.read()

# Find the last closing parenthesis of the list
last_paren_idx = code.rfind(")")

if last_paren_idx != -1:
    new_code = code[:last_paren_idx].rstrip()
    if not new_code.endswith(","):
        new_code += ",\n"
    new_code += items_code + "\n)\n"
    
    with open("app/src/main/java/com/example/sortsmart_se/model/WasteItem.kt", "w") as f:
        f.write(new_code)
    print("Successfully appended items!")
else:
    print("Could not find the end of the list.")
