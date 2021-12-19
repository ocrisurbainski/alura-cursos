import { Component, Input, OnInit } from '@angular/core';
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
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.user$ = this.userService.getUserLogged();
    }

}
