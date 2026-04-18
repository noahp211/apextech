# ApexTech

## What This Application Does
ApexTech is a Spring Boot web application for a technology product catalog.

Core features:
- Browse and search products by name, brand, and category
- Sort and paginate product listings
- Roles access control with three roles:
	- `ADMIN`: full access (product + user/role management)
	- `STAFF`: add product access
	- `CUSTOMER`: browsing only 
- Authentication (login/register)  

## How to Run With Docker

### Prerequisites


### 1. Step
Description

### 2. Step
Description

### 3. Step
Description

## Setup Steps and Environment Variables

### 1
Description

### 2
Description

### 3
Description

## Test Roles
Roles usernames and passwords to test the site:
- Admin: `admin / admin123`
- Staff: `staff / staff123`
- Customer: `customer / customer123`

## Contributions

### Noah Park
Description

### Bimal Gurung
Description

### Mushfika Hossain
- Navigation & Pages: 
    - Designed and refined the main navigation flow and page structure so users can move clearly between Home, Products, About, Roles, Login, and admin focused pages.
- Presentation & Styling: 
    -  Improved the overall UI polish with consistent spacing, typography, colors, cards, and form/table styling.
- Users and Registration:
    - Implemented and stabilized user registration with validation, including account creation and default role handling for new users.
- Login Page: 
    - Created the login page with validation, includes better messaging and role based test account visibility for easier evaluation.
- Created Pages
    - Created About and Roles pages to describe the application purpose and role guide descriptions
- Application Stability & Completion: 
    - Added and verified roles access control across ADMIN, `STAFF`, and `CUSTOMER`, including route restrictions and capability boundaries.
    - Added ability to manage users from admin page
    - Updated styling to be more refined
- README & Project Clarity: 
    - Structured and clarified project documentation to better explain application purpose, setup, and deployment.
