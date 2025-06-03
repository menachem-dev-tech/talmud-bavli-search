# Talmud Bavli Search

A Java-based application for searching and navigating the entire Talmud Bavli by tractate (masechet), page (daf), and folio (amud), or by free text.

[![GitHub Repo](https://img.shields.io/badge/GitHub-Talmud--Bavli--Search-blue?logo=github)](https://github.com/menachem-dev-tech/talmud-bavli-search)


---

## 📦 Features

- Accepts a raw `.txt` file (`bavli.txt`) of the entire Talmud
- On first run:
  - Splits content into hundreds of individual amud files in `data/shas/`
  - Creates `index.txt` with full hierarchical structure
  - Builds in-memory objects: Masechet → Daf → Amud
- On subsequent runs:
  - Loads directly from `index.txt` — faster startup and processing
- Supports:
  - Search by specific page (e.g. `"ברכות דף ב עמוד א"`)
  - Free-text search across all pages
  - Optional binary search within a single amud (after sorting)

---

## 🗂 Folder Structure

```

final_project/
├── data/
│   ├── bavli.txt       # Raw input file (entire Talmud)
│   ├── index.txt       # Auto-generated index file
│   └── shas/           # Auto-generated amud files (non-human-readable names)
├── src/
│   ├── entities/
│   │   ├── Amud.java
│   │   ├── Daf.java
│   │   └── Masechet.java
│   ├── Main.java
│   ├── Errors.java
│   └── ...other classes...
├── out/                # Compiled class files

```

---

## ▶️ Getting Started

1. Place `bavli.txt` inside the `data/` folder  
   > If `data/bavli.txt` is not found, the program will fall back to `data/bavli_sample.txt` for demonstration purposes.
2. Compile and run:

```bash
javac -d out src/**/*.java
java -cp out Main
```

> On first run, this will generate `shas/` and `index.txt`

---

## 🔍 Example Usage

```
Enter text to search:
→ אמר רבי אלעזר
Result: ברכות דף ב עמוד א

Enter daf (e.g. 'ברכות דף ג עמוד ב')
→ Opening file: data/shas/x92k3.txt
```

---

## ✅ Implemented

* [x] Amud file generation from full text
* [x] Object model: Masechet, Daf, Amud
* [x] Page and text search
* [x] Binary search mode (per amud)
* [x] Exception handling and validation
* [x] Standard Java project structure

---

## 🚧 To Do (Future Enhancements)

* [ ] Add Javadoc and code comments
* [ ] Improve file naming for readability
* [ ] Export index as JSON
* [ ] Add unit tests (JUnit)
* [ ] CLI flags or GUI (e.g., JavaFX)

---

## 📘 Notes

* File names in `data/shas/` are internal (not human-readable)
* The user interface is CLI-based
* Program requires Java 17+ (tested on Java 23)
* Due to its size, the full `bavli.txt` file is not included in this repository.  
To test the application, use the provided `bavli_sample.txt` in the `data/` folder.  
For full functionality, provide your own `bavli.txt` file in the same location.
* A small sample file `bavli_sample.txt` is included for demo purposes.
  If the full `bavli.txt` is missing, the program will automatically use the sample file instead.


---

## 🧪 Educational Note

This project was created as part of an ongoing learning process.  
Feedback, suggestions, or improvements are warmly welcome!

Please feel free to open an issue or submit a pull request if you'd like to contribute or help refine the design.


---

## 🙏 Author

This project was created as part of a personal learning journey in Java and computer science, with a focus on clean design, error handling, and indexing classical texts.

```

---