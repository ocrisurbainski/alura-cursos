import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PlatformDetectorService } from 'src/app/core/platform/platform-detector.service';
import { lowerCaseValidator } from 'src/app/shared/validators/lower-case.validator';
import { NewUser } from './new-user';
import { SignupService } from './signup.service';
import { UserNotTakenValidatorService } from './user-not-taken.validator.service';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    providers: [UserNotTakenValidatorService],
})
export class SignupComponent implements OnInit, AfterViewInit {

    @ViewChild('emailInput', {static:false}) emailInput: ElementRef<HTMLInputElement>;

    signupForm: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private userNotaTakenValidatorService: UserNotTakenValidatorService,
        private signupService: SignupService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService
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
        });
    }

    ngAfterViewInit(): void {
        this.platformDetectorService.isPlatformBrowser() && this.emailInput.nativeElement.focus();
    }

    signup() {
        const newUser = this.signupForm.getRawValue() as NewUser;
        this.signupService.signup(newUser).subscribe({
            next: () => this.router.navigate(['']),
            error: (err) => console.log(err)
        });
    }

}
