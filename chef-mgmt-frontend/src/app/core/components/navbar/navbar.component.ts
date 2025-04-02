import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ROUTES } from '../../config/routes.enum';


@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  protected readonly ROUTES = ROUTES;
}
