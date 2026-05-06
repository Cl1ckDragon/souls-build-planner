import { useEffect, useMemo, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getGames, getClassesByGame } from '../api/games'
import { createBuild } from '../api/builds'
import StatInput from '../components/build/StatInput'
import type { Game, GameClass } from '../types'

interface Stats {
  vigor: number
  mind: number
  endurance: number
  strength: number
  dexterity: number
  intelligence: number
  faith: number
  arcane: number
}

const STAT_LABELS: [keyof Stats, string][] = [
  ['vigor',        'Vigor'],
  ['mind',         'Mind'],
  ['endurance',    'Endurance'],
  ['strength',     'Strength'],
  ['dexterity',    'Dexterity'],
  ['intelligence', 'Intelligence'],
  ['faith',        'Faith'],
  ['arcane',       'Arcane'],
]

const DEFAULT_STATS: Stats = {
  vigor: 10, mind: 10, endurance: 10, strength: 10,
  dexterity: 10, intelligence: 10, faith: 10, arcane: 10,
}

function baseStats(cls: GameClass): Stats {
  return {
    vigor:        cls.baseVigor,
    mind:         cls.baseMind,
    endurance:    cls.baseEndurance,
    strength:     cls.baseStrength,
    dexterity:    cls.baseDexterity,
    intelligence: cls.baseIntelligence,
    faith:        cls.baseFaith,
    arcane:       cls.baseArcane,
  }
}

export default function CreateBuildPage() {
  const navigate = useNavigate()

  const [games, setGames] = useState<Game[]>([])
  const [classes, setClasses] = useState<GameClass[]>([])
  const [selectedGameId, setSelectedGameId] = useState<number | ''>('')
  const [selectedClassId, setSelectedClassId] = useState<number | ''>('')
  const [selectedClass, setSelectedClass] = useState<GameClass | null>(null)
  const [stats, setStats] = useState<Stats>(DEFAULT_STATS)
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [isPublic, setIsPublic] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [submitting, setSubmitting] = useState(false)

  useEffect(() => {
    getGames().then(res => setGames(res.data))
  }, [])

  useEffect(() => {
    if (selectedGameId === '') {
      setClasses([])
      setSelectedClassId('')
      setSelectedClass(null)
      return
    }
    getClassesByGame(selectedGameId).then(res => {
      setClasses(res.data)
      setSelectedClassId('')
      setSelectedClass(null)
    })
  }, [selectedGameId])

  useEffect(() => {
    const cls = classes.find(c => c.id === selectedClassId) ?? null
    setSelectedClass(cls)
    if (cls) setStats(baseStats(cls))
  }, [selectedClassId, classes])

  const level = useMemo(() => {
    if (!selectedClass) return '—'
    const spent = (Object.keys(stats) as (keyof Stats)[]).reduce(
      (sum, key) => sum + (stats[key] - (baseStats(selectedClass)[key])),
      0
    )
    return selectedClass.baseLevel + spent
  }, [stats, selectedClass])

  const setStat = (key: keyof Stats) => (value: number) =>
    setStats(prev => ({ ...prev, [key]: value }))

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (selectedGameId === '' || selectedClassId === '') {
      setError('Please select a game and class.')
      return
    }
    if (!title.trim()) {
      setError('Title is required.')
      return
    }
    setError(null)
    setSubmitting(true)
    try {
      const res = await createBuild({
        gameId: selectedGameId,
        classId: selectedClassId,
        title: title.trim(),
        description,
        ...stats,
        isPublic,
      })
      navigate(`/builds/${res.data.slug}`)
    } catch (err: unknown) {
      const msg = (err as { response?: { data?: { message?: string } } })
        ?.response?.data?.message
      setError(msg ?? 'Failed to save build.')
    } finally {
      setSubmitting(false)
    }
  }

  const inputCls =
    'w-full bg-souls-dark border border-souls-gold/30 rounded px-3 py-2 text-stone-200 ' +
    'focus:outline-none focus:border-souls-gold disabled:opacity-40'

  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="font-serif text-2xl text-souls-gold mb-8">New Build</h1>

      <form onSubmit={handleSubmit} className="space-y-6">
        {/* Game + Class */}
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-stone-400 mb-1">Game</label>
            <select
              value={selectedGameId}
              onChange={e => setSelectedGameId(e.target.value === '' ? '' : Number(e.target.value))}
              className={inputCls}
            >
              <option value="">Select game…</option>
              {games.map(g => (
                <option key={g.id} value={g.id}>{g.name}</option>
              ))}
            </select>
          </div>
          <div>
            <label className="block text-sm text-stone-400 mb-1">Starting Class</label>
            <select
              value={selectedClassId}
              onChange={e => setSelectedClassId(e.target.value === '' ? '' : Number(e.target.value))}
              disabled={classes.length === 0}
              className={inputCls}
            >
              <option value="">Select class…</option>
              {classes.map(c => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Title */}
        <div>
          <label className="block text-sm text-stone-400 mb-1">Build Title</label>
          <input
            type="text"
            maxLength={120}
            placeholder="e.g. Rivers of Blood Bleed Build"
            value={title}
            onChange={e => setTitle(e.target.value)}
            className={inputCls}
          />
        </div>

        {/* Stat Calculator */}
        <div className="bg-souls-stone rounded-lg p-4 border border-souls-gold/20">
          <div className="flex items-center justify-between mb-3">
            <h2 className="text-sm font-semibold text-stone-300 uppercase tracking-wide">
              Stats
            </h2>
            <div className="text-right">
              <span className="text-xs text-souls-ash">Level </span>
              <span className="text-souls-gold font-bold">{level}</span>
            </div>
          </div>

          {!selectedClass && (
            <p className="text-xs text-souls-ash py-2">
              Select a starting class to enable the stat calculator.
            </p>
          )}

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-x-8">
            {STAT_LABELS.map(([key, label]) => (
              <StatInput
                key={key}
                label={label}
                value={stats[key]}
                base={selectedClass ? baseStats(selectedClass)[key] : 1}
                onChange={setStat(key)}
                disabled={!selectedClass}
              />
            ))}
          </div>

          {selectedClass && (
            <p className="text-xs text-souls-ash mt-3">
              Base stat shown left of input &mdash; gold numbers are points spent above base.
            </p>
          )}
        </div>

        {/* Description */}
        <div>
          <label className="block text-sm text-stone-400 mb-1">Description</label>
          <textarea
            rows={4}
            maxLength={2000}
            placeholder="Describe the playstyle, weapon choices, key items…"
            value={description}
            onChange={e => setDescription(e.target.value)}
            className={`${inputCls} resize-none`}
          />
        </div>

        {/* Visibility */}
        <label className="flex items-center gap-3 cursor-pointer select-none">
          <input
            type="checkbox"
            checked={isPublic}
            onChange={e => setIsPublic(e.target.checked)}
            className="w-4 h-4 accent-souls-gold"
          />
          <span className="text-sm text-stone-300">Make this build public</span>
        </label>

        {error && <p className="text-red-400 text-sm">{error}</p>}

        <button
          type="submit"
          disabled={submitting}
          className="w-full py-2 bg-souls-gold/20 border border-souls-gold text-souls-gold rounded
                     hover:bg-souls-gold/30 transition-colors disabled:opacity-50"
        >
          {submitting ? 'Saving…' : 'Save Build'}
        </button>
      </form>
    </div>
  )
}
