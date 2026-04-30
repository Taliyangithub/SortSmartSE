table_data = """| Diapers                               | Put in restavfall                                                           |
| Wet wipes                             | Put in restavfall, do not flush                                             |
| Used tissues                          | Put in restavfall                                                           |
| Cotton pads                           | Put in restavfall                                                           |
| Cotton buds                           | Put in restavfall                                                           |
| Vacuum cleaner dust                   | Put in restavfall                                                           |
| Dirty paper towels                    | Put in restavfall                                                           |
| Greasy baking paper                   | Put in restavfall                                                           |
| Toothbrush                            | Put in restavfall                                                           |
| Dish sponge                           | Put in restavfall                                                           |
| Cleaning cloth                        | Put in restavfall                                                           |
| Broken ceramic plate                  | Wrap safely and put in restavfall                                           |
| Broken porcelain cup                  | Wrap safely and put in restavfall                                           |
| Drinking glass                        | Wrap safely and put in restavfall                                           |
| Mirror pieces                         | Wrap safely and put in restavfall, or take large pieces to recycling center |
| Cat litter                            | Put in restavfall                                                           |
| Dog poop bag                          | Put in restavfall                                                           |
| Cigarette butts                       | Put in restavfall                                                           |
| Chewing gum                           | Put in restavfall                                                           |
| Dust and hair                         | Put in restavfall                                                           |
| Small rubber items                    | Put in restavfall                                                           |
| Old dish brush                        | Put in restavfall                                                           |
| Toothpaste tube                       | Put in restavfall if not accepted as plastic packaging                      |
| Dirty plastic wrap                    | Put in restavfall                                                           |
| Dirty aluminum foil                   | Put in restavfall                                                           |
| Dirty pizza box parts                 | Put greasy parts in restavfall                                              |
| Receipts                              | Put in restavfall                                                           |
| Stickers and labels                   | Put in restavfall                                                           |
| Gift wrapping with glitter/plastic    | Put in restavfall                                                           |
| Ribbon and tape                       | Put in restavfall                                                           |
| Candle wax leftovers                  | Put in restavfall                                                           |
| Used makeup wipes                     | Put in restavfall                                                           |
| Empty makeup packaging mixed material | Put in restavfall if not recyclable                                         |
| Razor blade cartridge                 | Wrap safely and put in restavfall                                           |
| Broken pen                            | Put in restavfall                                                           |
| Pencil shavings                       | Put in restavfall                                                           |
| Small broken toys without battery     | Put in restavfall                                                           |
| Balloons                              | Put in restavfall                                                           |
| Disposable gloves                     | Put in restavfall                                                           |
| Face mask                             | Put in restavfall                                                           |"""

items = []
for line in table_data.strip().split("\n"):
    if line.strip().startswith("|"):
        parts = [p.strip() for p in line.split("|")]
        if len(parts) >= 3:
            name = parts[1]
            instr = parts[2]
            items.append((name, instr))

start_id = 54
new_items_code = ""

for i, (name, instr) in enumerate(items, start=start_id):
    keywords = f"{name.lower()}, trash, residual, restavfall"
    name = name.replace('"', '\\"')
    instr = instr.replace('"', '\\"')
    
    new_items_code += f'''    WasteItem(
        id = "{i}",
        name = "{name}",
        category = WasteCategory.RESIDUAL,
        action = RecycleAction.TRASH,
        instruction = "{instr}",
        howToDispose = "{instr}",
        preparation = "No special preparation needed unless specified.",
        why = "Items that cannot be recycled are incinerated to produce energy.",
        keywords = "{keywords}"
    ),
'''

new_items_code = new_items_code.strip()
if new_items_code.endswith(","):
    new_items_code = new_items_code[:-1]

with open("app/src/main/java/com/example/sortsmart_se/model/WasteItem.kt", "r") as f:
    code = f.read()

last_paren_idx = code.rfind(")")

if last_paren_idx != -1:
    new_code = code[:last_paren_idx].rstrip()
    if not new_code.endswith(","):
        new_code += ",\n"
    new_code += new_items_code + "\n)\n"
    
    with open("app/src/main/java/com/example/sortsmart_se/model/WasteItem.kt", "w") as f:
        f.write(new_code)
    print("Successfully appended items!")
else:
    print("Could not find the end of the list.")
