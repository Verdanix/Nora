**CONTRIBUTING.md**

# Contributing to Nora: Guidelines for Developers, Tinkerers, Employers, and End Users

Welcome to Nora's open-source community! We're excited to have you on board as we work together to create a comprehensive voice assistant that empowers users with customization options, flexibility, and control.

## Getting Started
Before diving into the contributing guidelines, please take a moment to familiarize yourself with our project:
- Visit our [README.md](./README.md) for an overview of Nora's features and capabilities.
- Review our [LICENSE](./LICENSE) to understand the terms of use.
- Review our [CODE OF CONDUCT](./CODE_OF_CONDUCT.md) to understand how to be safe in the community.
## Code Styles Guidelines
To ensure consistency throughout the project, please follow these code styles guidelines:
- **Style Guide**: Nora utilizes Google's Java style guide for coding standards. Please familiarize yourself with their guidelines for writing clean, readable, and maintainable code.
- **Documentation**: Use JavaDoc-style comments to document all classes, methods, constructors, and variables. Documentation should be consistently written and informative as to why certain choices are made and the impact.

## Tech Stack
Nora's tech stack is based on the following dependencies:
- **Audio Processing**: Utilize PiperTTS (Sherpa-onnx) for audio processing.
- **Wakeword Detection**: Employ Java OnnxRuntime for Wakeword detection.
- **STT**: Use Vosk for STT capabilities.
- **NLU**: Use Stanford CoreNLP for NLP capabilities.
- **Networking**: Manage networking using JavaCoap.
- **Configuration Management**: Utilize Gestalt for insecure data and H2 + Hibernate for secure data storage.
- **Plugin Framework**: Leverage PF4j for easy plugin integration.

## Testing
To ensure our codebase remains reliable and maintainable, we follow a rigorous testing approach:
- **Automated Testing**: Employ automated testing with TestNG to increase efficiency and reduce manual effort.
- **Unit Testing**: Conduct unit testing for individual components to validate internal logic.
- **Integration Testing**: Perform integration testing for combined functionality to ensure seamless interactions between components.
- **System Testing**: Conduct system testing for the entire system behavior to evaluate responsiveness, reliability, and capacity.

## Performance Attributes
To meet our performance standards, we prioritize:
- **Multithreading**: Utilize multithreading where possible to improve efficiency and responsiveness.
- **Algorithm/Data Structure Refinement**: Continuously refine algorithms and data structures to optimize performance.
- **Regular Updates and Bug Fixes**: Regularly update and fix bugs to maintain performance standards while ensuring security and stability.

## Development Phase
**Build and Deployment**
Deployment Strategy:
A full-release rollout would be employed, where all new features and updates are released simultaneously to the production environment. This approach ensures that users have access to the latest version of the software with each release.

Target Environments:
- **Development**: The primary workspace for developers, where developmental changes are made.
- **Testing**: An automated testing environment, where code is automatically tested after PRs and commits.
- **Production**: The final production environment, where releases are deployed for end-users.

**Automated Build and Deployment Process**
A CI/CD pipeline automates the build and deployment process, reducing errors and increasing efficiency. This includes:
- **PRS**: Pull requests are made into the development branch.
- **Testing**: Once the development for the next release is finished,  the code is tested in the testing branch.
- **Deployment**: Once the testing succeeds and all bugs have been fixed, A PR is made into the production branch and a release is made.

## Infrastructure and Monitoring
The necessary infrastructure includes:
- **Development, Testing, and Production Environments**: Identical configurations to replicate production-like conditions.
- **Regular Backups**: Backups will be created (in the form of zipped files) when running the update script, allowing for manual rollbacks if needed.

## Contributing Guidelines
1. Fork the repository and create a new branch.
2. Make changes in your branch.
3. Commit changes with meaningful commit messages.
4. Push changes to your remote repository.
5. Open a pull request against the development branch.
6. Participate in code reviews and discussions.
7. Follow testing guidelines and run automated tests.

By following these guidelines, we ensure that our project is developed in a collaborative, efficient, and secure manner.
