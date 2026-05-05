import apiClient from './client'
import type { AuthResponse } from '../types'

export const register = (username: string, email: string, password: string) =>
  apiClient.post<AuthResponse>('/api/auth/register', { username, email, password })

export const login = (usernameOrEmail: string, password: string) =>
  apiClient.post<AuthResponse>('/api/auth/login', { usernameOrEmail, password })
