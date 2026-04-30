items = [
    ("Plastic bottle", "WasteCategory.PLASTIC", "RecycleAction.RECYCLE", "Sort as plastic packaging.", "Empty contents before recycling.", "Recycled plastic saves energy and reduces landfill waste."),
    ("Plastic bag", "WasteCategory.PLASTIC", "RecycleAction.RECYCLE", "Sort as plastic packaging.", "Ensure it is clean and dry.", "Can be melted down and remade into new plastic bags."),
    ("Yogurt cup", "WasteCategory.PLASTIC", "RecycleAction.RECYCLE", "Sort as plastic packaging.", "Scrape out any leftover yogurt. No need to wash perfectly.", "Recycling reduces oil dependency."),
    ("Shampoo bottle", "WasteCategory.PLASTIC", "RecycleAction.RECYCLE", "Sort as plastic packaging.", "Empty the bottle. Remove the pump if it contains metal.", "Plastic packaging can be recycled many times."),
    ("Pizza box", "WasteCategory.PAPER", "RecycleAction.RECYCLE", "Sort as paper packaging.", "Remove all food waste. Grease stains are okay.", "Cardboard is highly recyclable and saves trees."),
    ("Newspaper", "WasteCategory.PAPER", "RecycleAction.RECYCLE", "Sort in the newspapers bin.", "Remove any plastic wrapping.", "Recycled paper is turned into new newsprint or toilet paper."),
    ("Cardboard box", "WasteCategory.PAPER", "RecycleAction.RECYCLE", "Sort as paper packaging.", "Flatten to save space in the bin.", "Cardboard fibers can be recycled up to seven times."),
    ("Receipt", "WasteCategory.RESIDUAL", "RecycleAction.TRASH", "Throw in residual waste.", "No preparation needed.", "Receipts contain thermal chemicals like BPA that ruin paper recycling."),
    ("Glass bottle", "WasteCategory.GLASS", "RecycleAction.RECYCLE", "Sort as glass packaging.", "Empty contents. Separate colored from clear glass.", "Glass can be recycled endlessly without loss of quality."),
    ("Glass jar", "WasteCategory.GLASS", "RecycleAction.RECYCLE", "Sort as glass packaging.", "Empty out food. Remove the metal lid.", "Recycling glass reduces emissions from manufacturing."),
    ("Food can", "WasteCategory.METAL", "RecycleAction.RECYCLE", "Sort as metal packaging.", "Empty out food and bend sharp edges inward.", "Metal can be recycled endlessly."),
    ("Soda can", "WasteCategory.METAL", "RecycleAction.RECYCLE", "Sort as metal packaging or use a Pant (deposit) machine.", "Do not crush if using a Pant machine.", "Recycling aluminum saves 95% of the energy needed to make new aluminum."),
    ("Aluminum foil", "WasteCategory.METAL", "RecycleAction.RECYCLE", "Sort as metal packaging.", "Scrape off major food residue.", "Aluminum is highly valuable and easily recycled."),
    ("Banana peel", "WasteCategory.FOOD", "RecycleAction.COMPOST", "Sort as food waste.", "Place in the brown paper compost bag.", "Turned into biogas and biofertilizer."),
    ("Apple core", "WasteCategory.FOOD", "RecycleAction.COMPOST", "Sort as food waste.", "Place in the brown paper compost bag.", "Creates nutrient-rich compost for local farms."),
    ("Coffee grounds", "WasteCategory.FOOD", "RecycleAction.COMPOST", "Sort as food waste.", "Filters can also be included in food waste.", "Excellent source of nitrogen for compost."),
    ("Tea bag", "WasteCategory.FOOD", "RecycleAction.COMPOST", "Sort as food waste.", "Remove the staple if there is one.", "Easily broken down in the composting process."),
    ("Eggshells", "WasteCategory.FOOD", "RecycleAction.COMPOST", "Sort as food waste.", "No preparation needed.", "Adds valuable calcium to biofertilizer."),
    ("Diaper", "WasteCategory.RESIDUAL", "RecycleAction.TRASH", "Throw in residual waste.", "Wrap securely in a plastic bag.", "Cannot be recycled due to human waste and mixed materials."),
    ("Wet wipes", "WasteCategory.RESIDUAL", "RecycleAction.TRASH", "Throw in residual waste. Never flush!", "Put in a plastic bag.", "Flushing wet wipes causes massive sewer clogs."),
    ("Toothbrush", "WasteCategory.RESIDUAL", "RecycleAction.TRASH", "Throw in residual waste.", "No preparation needed.", "Made of mixed plastics that standard plants cannot separate."),
    ("Sponge", "WasteCategory.RESIDUAL", "RecycleAction.TRASH", "Throw in residual waste.", "No preparation needed.", "Synthetic sponges are non-recyclable mixed plastics."),
    ("Battery", "WasteCategory.HAZARDOUS", "RecycleAction.HAZARDOUS", "Take to a battery collection point.", "Tape the ends of lithium batteries.", "Contains toxic heavy metals that must not leak into nature."),
    ("Paint", "WasteCategory.HAZARDOUS", "RecycleAction.HAZARDOUS", "Take to a recycling center.", "Keep in its original container.", "Toxic chemicals require specialized, safe disposal."),
    ("Medicine", "WasteCategory.HAZARDOUS", "RecycleAction.HAZARDOUS", "Return to a pharmacy (Apotek).", "Keep pills in blister packs, recycle the cardboard box.", "Protects aquatic life from hormonal and chemical disruption."),
    ("Mobile phone", "WasteCategory.HAZARDOUS", "RecycleAction.HAZARDOUS", "Take to electronics recycling.", "Erase your personal data first.", "Contains gold, copper, and rare earth metals that must be recovered."),
    ("Charger", "WasteCategory.HAZARDOUS", "RecycleAction.HAZARDOUS", "Take to electronics recycling.", "Tie the cable neatly.", "Copper wire is highly valuable and easily recycled."),
    ("Old clothes", "WasteCategory.RESIDUAL", "RecycleAction.RECYCLE", "Donate or put in textile recycling.", "Wash them and seal in a plastic bag.", "Textile production uses massive resources; reuse is critical."),
    ("Light bulb", "WasteCategory.HAZARDOUS", "RecycleAction.HAZARDOUS", "Take to electronics recycling.", "Handle with care.", "May contain hazardous gases like mercury."),
    ("Cooking oil", "WasteCategory.HAZARDOUS", "RecycleAction.RECYCLE", "Bottle it and take to recycling center.", "Let it cool and pour into a plastic bottle.", "Prevents severe clogs in the sewer system and can be turned into biodiesel.")
]

with open("/Users/priyataliyan/AndroidStudioProjects/SortSmartSE/items_output.txt", "w") as f:
    for i, (name, cat, action, instr, prep, why) in enumerate(items, start=24):
        keywords = name.lower()
        f.write(f'''    WasteItem(
        id = "{i}",
        name = "{name}",
        category = {cat},
        action = {action},
        instruction = "{instr}",
        howToDispose = "{instr}",
        preparation = "{prep}",
        why = "{why}",
        keywords = "{keywords}"
    ),\n''')
