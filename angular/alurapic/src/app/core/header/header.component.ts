import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../user/user';
import { UserService } from '../user/user.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {

    @Input() title: string = '';

    user$: Observable<User|null>;

    constructor(
        private userService: UserService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.user$ = this.userService.getUserLogged();
    }

    logout() {
        this.userService.logout();
        this.router.navigate(['']);
    }

}
