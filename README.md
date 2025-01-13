# eva

Eva is a lightweight and efficient code editor written entirely in Java, designed to be simple, fast, and extensible. It is ideal for quick editing tasks and lightweight development workflows.

> [!IMPORTANT]
> This project is a work in progress. Contributions and feedback are welcome!
> 
## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Installation
1. Clone this repository:
   ```bash
   https://github.com/wiirios/eva.git
   ```
2. Navigate to the project directory:
   ```bash
   cd eva
   ```
3. Build and run the project:
``` bash
	mvn clean compile
	mvn dependency:copy-dependencies
	java -cp "target/classes;target/dependency/*" org.william.eva.Main
```

## Roadmap

### To Do
- [ ] Syntax highlighting.
- [ ] Code autocompletion with suggestions.
- [ ] File explorer panel for opening and managing files.
- [ ] Code formatting (auto-indent and style enforcement).
- [ ] Undo/redo support.
- [ ] Compile and run the code directly from the editor.

## MIT License
This project is licensed under MIT license, read more at <span><a href="https://docs.github.com/pt/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/licensing-a-repository">docs.github</span>