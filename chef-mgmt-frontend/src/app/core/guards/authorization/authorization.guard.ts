import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { UserResponse } from '../../../feature/profile/models/user-response.model';
import { ROUTES } from '../../config/routes.enum';
import { AuthService } from '../../services/auth/auth.service';


export const hasAuthorization: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const router: Router = inject(Router);
  const requiredRoles: string[] = route.data['requiredRoles'];
  const isSelf: boolean = route.data['isSelf'];
  const authService: AuthService = inject(AuthService);
  const loggedUser: UserResponse = authService.userSubject.value;

  return requiredRoles.includes(loggedUser?.role) || isSelf && loggedUser?.id === route.params['id']
    ? true
    : router.navigateByUrl(ROUTES.FORBIDDEN).then();
};
