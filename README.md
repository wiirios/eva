# eva

<p>
  <img src="https://img.shields.io/github/license/wiirios/eva.svg" alt="License Badge">
  <img src="https://img.shields.io/github/issues/wiirios/eva.svg" alt="Issues Badge">
  <img src="https://img.shields.io/github/stars/wiirios/eva.svg" alt="Stars Badge">
</p>

Eva is a lightweight and efficient code editor written entirely in Java, designed to be simple, fast, and extensible. It is ideal for quick editing tasks and lightweight development workflows.

> [!IMPORTANT]
> This project is a work in progress. Contributions and feedback are welcome!

> [!WARNING]
> Linux and macOS users might encounter issues while using the application. Some features are not yet implemented on these platforms. If you experience any problems, please consider opening an issue.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.5.0 or higher

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
- [ ] Create a GitBook  
- [ ] Create a workflow guide
- [ ] Add a file explorer panel for opening and managing files  
- [ ] Add an option to save custom settings  

## MIT License
This project is licensed under MIT license, read more at <span><a href="https://docs.github.com/pt/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/licensing-a-repository">docs.github</span>