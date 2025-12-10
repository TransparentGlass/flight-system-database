# flight-system-database


---

# 📘 Project Name
CC14 finals project that uses mySQL to create a database


---

## 🚀 Getting Started

### Prerequisites
- Install [Java 21+](https://adoptium.net/)  
- Install [Maven](https://maven.apache.org/)  
- Install [Git](https://git-scm.com/)  
- Use [VS Code](https://code.visualstudio.com/) or your preferred IDE

### Clone the Repository
```bash
git clone https://github.com/your-team/project-name.git
cd project-name
```

### Run the Project
```bash
mvn clean install
mvn exec:java
```

---

## 🌱 Branching Workflow

- **main** → stable, production-ready code  
- **develop** → integration branch for testing features together  
- **feature/*branch-name*** → individual tasks (e.g., `feature/login-ui`)  

### Creating a Feature Branch
```bash
git checkout -b feature/your-task
```

### Pushing Your Work
```bash
git add .
git commit -m "Add login UI"
git push origin feature/your-task
```

---

## 🔄 Pull Requests

1. Open a PR from your `feature/*` branch into `develop`.  
2. At least **one teammate must review** before merging.  
3. CI/CD will run automatically — fix any errors before merging.  

---

## ✅ Contribution Rules

- Write clear commit messages (`Add StudentDAO`, `Fix login bug`).  
- Keep changes small and focused.  
- Don’t push directly to `main`.  
- Document new classes or methods briefly in code comments.  
- Run `mvn test` before opening a PR.  

---

## 📋 Task Tracking

Columns: **To Do → In Progress → Done**.  
Each teammate picks tasks from the board.

---

## 🧪 Testing

Run unit tests locally:
```bash
mvn test
```

---

## 📖 Documentation

- Add instructions for new features in the README or wiki.  
- Keep database schema and API contracts updated.  

---

# How to connect to the database (for matthew and haiana)
They only need to:

 - Install Tailscale and join your tailnet.
 - Get your own 100.x.x.x Tailscale IP automatically.
 - Use your server’s Tailscale IP in their connection string in the cmd:
    mysql -h <100.108.168.84> -u projectuser -p

or in jbdc:
String url = "jdbc:mysql://<your-tailscale-ip>:3306/projectdb";
String user = "projectuser";
String password = "strongpassword";
