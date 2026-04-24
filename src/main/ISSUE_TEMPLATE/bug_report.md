name: Bug Report
description: Report any issue in the system
title: "[Bug] "
labels: ["Accounts"]  

body:
  - type: textarea
    id: problem
    attributes:
      label: What is the problem?
      placeholder: Describe what went wrong
    validations:
      required: true

  - type: textarea
    id: steps
    attributes:
      label: Steps to reproduce
      placeholder: 1. Go to...
                   2. Click...
                   3. See error

  - type: textarea
    id: expected
    attributes:
      label: What should happen?

  - type: textarea
    id: extra
    attributes:
      label: Extra details (optional)
      placeholder: Error messages, screenshots, logs