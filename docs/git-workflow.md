# Git Version Control Workflow

This document demonstrates the Git usage and workflow implemented during the development of the Bank Account Management System.

## Initial Setup
```bash
git init
git add .
git commit -m "Initial refactoring for clean code"
```

## Feature Development
The system refactoring was performed on a dedicated feature branch.

```bash
git branch feature/refactor
git checkout feature/refactor
# [Small commits made during development]
git commit -m "Implement custom exceptions and model refactoring"
git commit -m "Implement ValidationUtils and account managers"
```

## Testing Branch
JUnit tests were added and verified on a separate testing branch.

```bash
git branch feature/testing
git checkout feature/testing
git commit -m "Add JUnit tests for transactions"
```

## Merging and Cherry-picking
Examples of merging features and cherry-picking critical fixes.

```bash
# Merging testing into main
git checkout main
git merge feature/testing

# Cherry-picking a specific commit
git checkout main
git log
git cherry-pick <commit-hash>

# Pushing to origin
git push origin main
```

## Requirements Checklist
- Use descriptive commit messages.
- Work on feature branches.
- Ensure tests pass before merging.
