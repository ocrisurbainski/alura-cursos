import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { lowerCaseValidator } from 'src/app/shared/validators/lower-case.validator';
import { NewUser } from './new-user';
import { SignupService } from './signup.service';
import { UserNotTakenValidatorService } from './user-not-taken.validator.service';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
})
export class SignupComponent implements OnInit {

    signupForm: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private userNotaTakenValidatorService: UserNotTakenValidatorService,
        private signupService: SignupService,
        private router: Router
    ) { }

    ngOnInit(): void {

        this.signupForm = this.formBuilder.group({
            email: ['', 
                [
                    Validators.required, 
                    Validators.email,
                ]
            ],
            userName: ['', 
                [
                    Validators.required,
                    Validators.minLength(2),
                    Validators.maxLength(40),
                    lowerCaseValidator,
                ],
                this.userNotaTakenValidatorService.checkUserNameTaken()
            ],
            fullName: ['', 
                [
                    Validators.required,
                    Validators.minLength(2),
                    Validators.maxLength(30),
                ],
            ],
            password: ['', 
                [
                    Validators.required,
                    Validators.minLength(8),
                    Validators.maxLength(14),
                ],
            ],
        })
    }

    signup() {
        debugger;
        const newUser = this.signupForm.getRawValue() as NewUser;
        this.signupService.signup(newUser).subscribe({
            next: () => this.router.navigate(['']),
            error: (err) => console.log(err)
        });
    }

}
