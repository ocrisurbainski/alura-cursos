import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth/auth.service';
import { PlatformDetectorService } from 'src/app/core/platform/platform-detector.service';

@Component({
    selector: 'app-signin',
    templateUrl: './signin.component.html',
})
export class SigninComponent implements AfterViewInit {

    loginForm: FormGroup;

    @ViewChild('usernameInput', {static: false}) usernameInput: ElementRef<HTMLInputElement>;

    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService) { 

        this.loginForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3)]],
            password: ['', [Validators.required, Validators.minLength(3)]]
        });
    }

    ngAfterViewInit(): void {
        this.platformDetectorService.isPlatformBrowser() && this.usernameInput.nativeElement.focus();
    }

    login() {

        const data = this.loginForm.getRawValue(),
            username = data.username,
            password = data.password;

        this.authService.authenticate(username, password).subscribe({
            next: () => this.router.navigate(['user', username]),
            error: (err) => {
                this.loginForm.reset();
                this.platformDetectorService.isPlatformBrowser() && this.usernameInput.nativeElement.focus();
                alert('Invalid user name or password');
            }
        });
    }

}
