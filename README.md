# Clojure Fibonacci Project

This is a simple Clojure project that calculates Fibonacci numbers. This README provides instructions on setting up your Clojure development environment on macOS.

## Prerequisites

- macOS
- [Homebrew](https://brew.sh/) (recommended for installing Clojure CLI)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community or Ultimate edition)

## Installing Clojure CLI on macOS

### Using Homebrew (Recommended)

1. If you don't have Homebrew installed, install it by running:
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. Install Clojure CLI using Homebrew:
   ```bash
   brew install clojure/tools/clojure
   ```

3. Verify the installation:
   ```bash
   clj --version
   ```

### Alternative Installation (Official Installer)

1. Download the installer script:
   ```bash
   curl -O https://download.clojure.org/install/brew-install.sh
   ```

2. Make the script executable:
   ```bash
   chmod +x brew-install.sh
   ```

3. Run the installer:
   ```bash
   sudo ./brew-install.sh
   ```

4. Verify the installation:
   ```bash
   clj --version
   ```

## Installing Cursive Plugin for IntelliJ IDEA

1. Open IntelliJ IDEA

2. Go to **IntelliJ IDEA → Preferences** (or press `⌘,`)

3. Navigate to **Plugins → Marketplace**

4. Search for "Cursive"

5. Click **Install** and restart IntelliJ IDEA when prompted

6. After restart, go to **IntelliJ IDEA → Preferences → Languages & Frameworks → Clojure**

7. Configure the Clojure installation path if needed

## Running the Fibonacci Program

To verify that your Clojure environment is working correctly, you can run the Fibonacci program using one of the following methods:

### Using Clojure CLI

Navigate to the project root directory and run:

```bash
clj -M -m Fibs
```

### Using Java Directly

If you prefer to use Java directly with the Clojure JAR files:

```bash
java -cp "lib/*:src" clojure.main -m Fibs
```

### Using IntelliJ IDEA with Cursive

1. Open the project in IntelliJ IDEA
2. Right-click on the `src/Fibs.clj` file in the Project panel
3. Select **Run 'Fibs'** or **Run 'Fibs.main'**

Alternatively, you can set up a Run Configuration:

1. Go to **Run → Edit Configurations**
2. Click the **+** button and select **Clojure REPL → Local**
3. Name your configuration (e.g., "Fibs REPL")
4. Set the working directory to your project root
5. In the "Parameters" field, add `-m Fibs`
6. Click **Apply** and **OK**
7. Now you can run this configuration from the Run menu or by pressing **Ctrl+R**

### Expected Output

The program will calculate the 20th Fibonacci number and display the result along with the time it took to calculate:

```
Calculating fib(20)...
6765
"Elapsed time: X msecs"
```

## Project Structure

- `src/Fibs.clj`: Contains the Fibonacci calculation functions
- `lib/`: Contains Clojure JAR dependencies

