import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginGuardService } from '../core/auth/login-guard.service';
import { HomeComponent } from './home.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        canActivate: [LoginGuardService],
        children: [
            {
                path: '',
                component: SigninComponent,
            },
            {
                path: 'signup',
                component: SignupComponent,
            },
        ]
    },
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
    ],
    exports: [
        RouterModule,
    ]
})
export class HomeRoutingModule { }
