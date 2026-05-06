import apiClient from './client'
import type { Game, GameClass } from '../types'

export const getGames = () =>
  apiClient.get<Game[]>('/api/games')

export const getClassesByGame = (gameId: number) =>
  apiClient.get<GameClass[]>(`/api/games/${gameId}/classes`)
