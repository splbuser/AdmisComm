# ADMISSIONS COMMITTEE PROJECT

The system has a list of faculties for which it is necessary to implement the possibility of sorting:
* `by name` (a-z, z-a);
* `by the number of budget places`;
* `by the total number of places`.

The applicant registers in the system. During registration, it is necessary to enter the `name`, `email`, `city`, 
`region`, `FULL name of the educational institution` (*optional*: `attach a scan of the certificate with grades`). 
The entrant can register for one or more faculties. When registering for the faculty, the student enters 
the results of the relevant subjects, as well as grades from the certificate.

The `system administrator` has the rights:
* adding, deleting, or editing faculty;
* blocking or unblocking the applicant;
* adding applicants' results to the Statement;
* finalization the Statement for enrollment.

After finalizing the Statement, the system calculates the amount of points and determines the applicants 
enrolled in the educational institution for budget places and on the contract. (If you wish you can add a 
notification about the result of enrollment in a certain form of education, as well as not enrollment by 
sending an email to the applicant).


## Entities Class diagram
![alt text](DB_schema.png "DB Scheme")

### Screenshots
![alt text](admin-applicants.png "Manege Users Page")
![alt text](admin-create-faculty.png "Create Faculty Page")
![alt text](admin-enrollment.png "Enrollment Page")
![alt text](admin-faculty.png "Manage Faculty Page")
![alt text](admin-statement-sort.png "Statement Page")
![alt text](error.png "Error Page")
![alt text](index_en.png "Index EN Page")
![alt text](index.png "Index Page")
![alt text](login.png "Login Page")
![alt text](register.png "Register Page")
![alt text](user-index.png "User Index Page")
![alt text](user-info_1.png "User Info Page")
![alt text](user-info_2.png "Usr Info Page")
![alt text](user-input.png "User Input Page")
![alt text](user-reg.png "User Registration Page")